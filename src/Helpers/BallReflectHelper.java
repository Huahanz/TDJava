package Helpers;

import java.lang.reflect.*;

import balls.Ball;


public class BallReflectHelper {

	public void implementClassAndMethod(String className, String methodName) {
		Class<?> c;
		try {
			c = Class.forName(className);
			Method m = c.getMethod(methodName, new Class[0]);
			m.invoke(c.newInstance(), new Object[0]);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	public Object implementMethodNoNewClass(Ball b, String methodName,
			Object[] args) {
		if (methodName == null || b == null) {
			return null;
		}

		Object result = null;
		Method m = null;
		Class[] argsClass = new Class[args.length];

		int i = 0;
		for (Object o : args) {
			argsClass[i++] = o.getClass();
		}

		for (int temp = 0; temp < argsClass.length; temp++) {

			if (argsClass[temp].getName().equals("java.lang.Object")) {
				continue;
			}
			while (!argsClass[temp].getName().equals("java.lang.Object")) {
				try {
					if (this.isMethodExist(b, methodName, argsClass)) {
						m = b.getClass().getMethod(methodName, argsClass);
						break;
					}
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				argsClass[temp] = (Class) argsClass[temp]
						.getGenericSuperclass();
			}
		}

		try {
			m = b.getClass().getMethod(methodName, argsClass);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return this.invokeMethod(m, b, args);

	}

	public Object invokeMethod(Method m, Ball b, Object[] args) {
		try {
			return m.invoke(b, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public Class[] optimizeMethodArgsToBall(Class[] argsClass,
	// String optimizeToClassName) {
	// filter f = new filter();
	// for (int i = 0; i < argsClass.length; i++) {
	// if (f.isSubclass(optimizeToClassName, argsClass[i].getName())) {
	// while (argsClass[i].getName() != optimizeToClassName) {
	// argsClass[i] = (Class) argsClass[i].getGenericSuperclass();
	// }
	//
	// }
	// }
	// return argsClass;
	// }

	public boolean isMethodExist(Ball b, String methodName, Class[] argsClass)
			throws SecurityException, NoSuchMethodException {
		Method[] ms = b.getClass().getMethods();
		for (Method m : ms) {
			if (this.isEqualArgsClass(argsClass, m.getParameterTypes())
					&& m.getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEqualArgsClass(Class[] argsClass1, Class[] argsClass2) {
		if (argsClass1.length != argsClass2.length) {
			return false;
		} else {
			for (int i = 0; i < argsClass1.length; i++) {
				if (!argsClass1[i].equals(argsClass2[i])) {
					return false;
				}
			}
			return true;
		}
	}

	// public Object testMethodNoNewClass(destest b, String methodName,
	// Object[] args) {
	//
	// Method m = null;
	// Class[] argsClass = new Class[args.length];
	// int i = 0;
	// for (Object o : args) {
	// argsClass[i] = args[i].getClass();
	// // System.out.println(argsClass[i].getName());
	// i++;
	// }
	//
	// try {
	// m = b.getClass().getMethod(methodName, argsClass);
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// }
	//
	// Object result = null;
	//
	// try {
	// result = m.invoke(b, args);
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// return result;
	//
	// }

	public Object newInstance(String className, Object[] args) throws Exception {
		Class newoneClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = newoneClass.getConstructor(argsClass);
		return cons.newInstance(args);
	}

	public boolean subclassTester(String screenClassName, String inClassName) {
		Class temp = null;
		try {
			temp = Class.forName(inClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		while (!temp.getName().equals("java.lang.Object")) {
			if (temp.getName().equals(screenClassName)) {
				return true;
			}
			temp = (Class) temp.getGenericSuperclass();
		}
		return false;
	}
}
