package atoms;

import balls.Ball;

public abstract class Atom
{
	public static Object exe(Ball ball){
		try {
			throw new Exception("Call Atom.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}