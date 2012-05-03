package org.test;

import org.apollo.backend.codec.FrontendPacketBuilder;
import org.apollo.backend.codec.FrontendPacketReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Testing the Gson API.
 * @author Steve
 */
public final class GsonTest {

	/**
	 * Testing.
	 * @param args Arguments.
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		Gson gson = new GsonBuilder().create();
		FrontendPacketBuilder builder = new FrontendPacketBuilder("sendYell");
		builder.addParameter("user", "Steve");
		FrontendPacketReader reader = new FrontendPacketReader(builder.toFrontendPacket());
		System.out.println(gson.toJson(reader.getParameters()));
	}
}
