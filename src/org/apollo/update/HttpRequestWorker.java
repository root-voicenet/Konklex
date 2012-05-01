package org.apollo.update;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apollo.backend.Dispatch;
import org.apollo.fs.IndexedFileSystem;
import org.apollo.game.model.Config;
import org.apollo.update.resource.CombinedResourceProvider;
import org.apollo.update.resource.HypertextResourceProvider;
import org.apollo.update.resource.ResourceProvider;
import org.apollo.update.resource.VirtualResourceProvider;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

/**
 * A worker which services HTTP requests.
 * @author Graham
 */
public final class HttpRequestWorker extends RequestWorker<HttpRequest, ResourceProvider> {

	/**
	 * The value of the server header.
	 */
	private static final String SERVER_IDENTIFIER = Config.SERVER_NAME + "/3.2";

	/**
	 * The directory with web files.
	 */
	private static final File WWW_DIRECTORY = new File("./data/www/");

	/**
	 * The default character set.
	 */
	private static final Charset CHARACTER_SET = Charset.forName("ISO-8859-1");

	/**
	 * Creates the HTTP request worker.
	 * @param dispatcher The dispatcher.
	 * @param fs The file system.
	 */
	public HttpRequestWorker(UpdateDispatcher dispatcher, IndexedFileSystem fs) {
		super(dispatcher, new CombinedResourceProvider(new VirtualResourceProvider(fs), new HypertextResourceProvider(WWW_DIRECTORY)));
	}

	/**
	 * Creates an error page.
	 * @param status The HTTP status.
	 * @param description The error description.
	 * @return The error page as a buffer.
	 */
	private ChannelBuffer createErrorPage(HttpResponseStatus status, String description) {
		String title = status.getCode() + " " + status.getReasonPhrase();
		StringBuilder bldr = new StringBuilder();
		bldr.append("<!DOCTYPE html><html><head><title>");
		bldr.append(title);
		bldr.append("</title></head><body><h1>");
		bldr.append(title);
		bldr.append("</h1><p>");
		bldr.append(description);
		bldr.append("</p><hr /><address>");
		bldr.append(SERVER_IDENTIFIER);
		bldr.append(" Server</address></body></html>");
		return ChannelBuffers.copiedBuffer(bldr.toString(), Charset.defaultCharset());
	}

	/**
	 * Gets the MIME type of a file by its name.
	 * @param name The file name.
	 * @return The MIME type.
	 */
	private String getMimeType(String name) {
		if (name.endsWith(".htm") || name.endsWith(".html")) {
			return "text/html";
		} else
			if (name.endsWith(".css")) {
				return "text/css";
			} else
				if (name.endsWith(".js")) {
					return "text/javascript";
				} else
					if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
						return "image/jpeg";
					} else
						if (name.endsWith(".gif")) {
							return "image/gif";
						} else
							if (name.endsWith(".png")) {
								return "image/png";
							} else
								if (name.endsWith(".txt")) {
									return "text/plain";
								}
		return "application/octect-stream";
	}

	/**
	 * Request url parameters.
	 * @param url the url
	 * @return the url parameters
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Map<String, List<String>> getUrlParameters(String url) throws IOException {
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		String[] urlParts = url.split("\\?");
		if (urlParts.length > 1) {
			String query = urlParts[1];
			for (String param : query.split("&")) {
				String pair[] = param.split("=");
				String key = URLDecoder.decode(pair[0], "UTF-8");
				String value = "";
				if (pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8");
				}
				List<String> values = params.get(key);
				if (values == null) {
					values = new ArrayList<String>();
					params.put(key, values);
				}
				values.add(value);
			}
		}
		return params;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.update.RequestWorker#nextRequest(org.apollo.update.UpdateDispatcher)
	 */
	@Override
	protected ChannelRequest<HttpRequest> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException {
		return dispatcher.nextHttpRequest();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.update.RequestWorker#service(java.lang.Object, org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@Override
	protected void service(ResourceProvider provider, Channel channel, HttpRequest request) throws IOException {
		String path = request.getUri();
		ByteBuffer buf = provider.get(path);
		ChannelBuffer wrappedBuf;
		HttpResponseStatus status = HttpResponseStatus.OK;
		if (path.endsWith("/")) {
			path += "index.html";
			request.setUri(path);
		}
		String mimeType = getMimeType(request.getUri());
		if (path.startsWith("/api/call")) {
			mimeType = "application/json";
			try {
				wrappedBuf = Dispatch.getInstance().decode(getUrlParameters(path));
			} catch (Exception e) {
				mimeType = "text/html";
				status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
				wrappedBuf = createErrorPage(status, "The page you requested could not be accessed.");
			}
		} else
			if (buf == null) {
				status = HttpResponseStatus.NOT_FOUND;
				wrappedBuf = ChannelBuffers.wrappedBuffer(provider.get("/404.html"));
				mimeType = "text/html";
			} else {
				wrappedBuf = ChannelBuffers.wrappedBuffer(buf);
			}
		HttpResponse resp = new DefaultHttpResponse(request.getProtocolVersion(), status);
		resp.setHeader("Date", new Date());
		resp.setHeader("Server", SERVER_IDENTIFIER);
		resp.setHeader("Content-type", mimeType + ", charset=" + CHARACTER_SET.name());
		resp.setHeader("Cache-control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Expires", new Date(0));
		resp.setHeader("Connection", "close");
		resp.setHeader("Content-length", wrappedBuf.readableBytes());
		resp.setChunked(false);
		resp.setContent(wrappedBuf);
		channel.write(resp).addListener(ChannelFutureListener.CLOSE);
	}
}
