package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.LoginMethod;

/**
 * An {@link MethodDecoder} for the {@link LoginMethod}
 * @author Steve
 */
public final class LoginMethodDecoder extends MethodDecoder<LoginMethod> {

    @Override
    public LoginMethod decode(FrontendPacket packet) {
	FrontendPacketReader reader = new FrontendPacketReader(packet);
	String player = reader.getString("player");
	String pass = reader.getString("pass");
	return new LoginMethod(player, pass);
    }

}
