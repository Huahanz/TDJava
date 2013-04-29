package Tests;

import balls.ActiveBall;
import balls.FastBall;

public class ReflectionTest{
	public static void main(String[] args){
		FastBall fb = new FastBall(100, 100);
		System.out.println(fb instanceof ActiveBall);
	}
}