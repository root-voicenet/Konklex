package org.apollo.util;

import java.lang.reflect.Constructor;

/**
 * A utility that initiates a new class.
 * @author Steve
 */
public final class ConstructionUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object initiate(String className, Object... iView) {
		try {
			Class iViewClass = iView.getClass();
			Class clazz = Class.forName(className);
			try {
				Constructor ctor = clazz.getDeclaredConstructor(iViewClass);
				ctor.setAccessible(true);
				return ctor.newInstance(iView);

			}
			catch (NoSuchMethodException e) {
				Constructor[] constructors = clazz.getDeclaredConstructors();
				for (Constructor c : constructors) {
					if (c.getParameterTypes().length > 1)
						continue;
					Class type = c.getParameterTypes()[0];
					if (type.isAssignableFrom(iView.getClass())) {
						return c.newInstance(type.cast(iView));
					}

				}
			}
			return null;

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
