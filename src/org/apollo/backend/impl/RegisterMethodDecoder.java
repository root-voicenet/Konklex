package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.RegisterMethod;

/**
 * An {@link MethodDecoder} for the {@link RegisterMethod}
 * @author Steve
 */
public final class RegisterMethodDecoder extends MethodDecoder<RegisterMethod> {

	@Override
	public RegisterMethod decode(FrontendPacket packet) {
		return new RegisterMethod();
	}

}
