package Tests;

import FrontEnd.Balls.ActiveBall;
import FrontEnd.Balls.FastBall;

public class ReflectionTest{
	public static void main(String[] args){
		FastBall fb = new FastBall();
		System.out.println(fb instanceof ActiveBall);
	}
}