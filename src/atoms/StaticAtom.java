package Atoms;

import Balls.AtomBall;
import Helpers.LogHelper;

/**
 * Static Atom is the internal atoms. Cannot be called from Balls. Can only be
 * called from other Atoms.
 * So this means the static atoms are just formular wrappers. Cannot upgrade. 
 */
abstract class StaticAtom extends Atom {
	public Object exe(AtomBall ball){
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogHelper.warn("Call Static Atom from outsice.");
		return null;
	}
}

class CalculateAngleAtom extends StaticAtom{
	public static int calculateAngle(double thisX, double thisY, double toX, double toY) {
		double dx = Math.abs(toX - thisX);
		int r = 0;
		if (toY > thisY - dx * 0.7 && toY < thisY + dx * 0.7)
			r = 2;
		else if (toY >= thisY + dx * 0.7 && toY <= thisY + dx * 2.1)
			r = 1;
		else if (toY > thisY + dx * 2.1)
			r = 0;
		else if (toY <= thisY - dx * 0.7 && toY >= thisY - dx * 2.1)
			r = 3;
		else
			r = 4;
		if (thisX < toX) {
			return 8 - r;
		}
		return r;
	}
}
