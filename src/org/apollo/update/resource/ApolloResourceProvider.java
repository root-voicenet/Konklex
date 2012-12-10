package org.apollo.update.resource;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apollo.game.command.Command;
import org.apollo.util.DefaultUtil;
import org.apollo.util.TextUtil;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * A {@link ResourceProvider} which provides additional hypertext resources.
 * @author Graham
 */
public final class ApolloResourceProvider extends ResourceProvider {

	/**
	 * The base directory from which documents are served.
	 */
	private final File base;

	/**
	 * Creates a new hypertext resource provider with the specified base directory.
	 * @param base The base directory.
	 */
	public ApolloResourceProvider(File base) {
		this.base = base;
	}

	@Override
	public boolean accept(String path) throws IOException {
		if (path.contains(".apollo"))
			return true;
		return false;
	}

	@Override
	public ByteBuffer get(Channel channel, HttpRequest request, String path) throws IOException {
		try {
			StringBuilder builder = new StringBuilder();
			File f = new File(base, path.split("\\?")[0]);
			if (!f.exists())
				return null;
			List<String> lines = Files.readAllLines(Paths.get(f.getAbsolutePath()), Charset.defaultCharset());
			Map<String, List<String>> parameters = TextUtil.getUrlParameters(path);
			for (String line : lines) {
				String[] args = line.split(" ");
				for (int i = 0; i < args.length; i++) {
					String argument = args[i];
					if (argument.startsWith("%")) {
						String temp = argument.replaceFirst("%", "").replaceFirst("._if", "");
						String replacement = parameters.get(temp).toString().replace("[", "").replace("]", "");
						if (argument.endsWith("._if")) {
							String key = args[i + 1];
							if (key.equals(replacement)) {
								line = line.replaceFirst(key + " ", "");
								line = line.replaceFirst(argument + " ", "");
							}
							else
								return null;
						} else {
							line = line.replaceFirst(argument, replacement);
						}
					}
				}
				Command command = DefaultUtil.createCommand(line);
				DefaultUtil.executeCommand(command);
				builder.append(command + "\r\n");
			}
			return ChannelBuffers.copiedBuffer(builder.toString(), Charset.defaultCharset()).toByteBuffer();
		}
		catch (Exception e) {
			return null;
		}
	}

}
