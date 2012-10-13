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
			if (iView == null) {
				return Class.forName(className).newInstance();
			}
			else {
				Class iViewClass = iView[0].getClass();
				Class clazz = Class.forName(className);
				try {
					Constructor ctor = clazz.getDeclaredConstructor(iViewClass);
					ctor.setAccessible(true);
					return ctor.newInstance(iView[0]);

				}
				catch (NoSuchMethodException e) {
					e.printStackTrace();
					Constructor[] constructors = clazz.getDeclaredConstructors();
					for (Constructor c : constructors) {
						if (c.getParameterTypes().length > 1)
							continue;
						Class type = c.getParameterTypes()[0];
						for (Object idk : iView) {
							if (type.isAssignableFrom(idk.getClass())) {
								return c.newInstance(type.cast(idk));
							}
						}
					}
				}
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
