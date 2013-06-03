package Helpers;
import java.util.HashMap;

import balls.Ball;
/**
 * Map ballID to ball reference
 * Many reference. Read and write seperate. Shouldn't have multithread issues. 
 */
public class BallCache
{
	HashMap<Integer, Ball> ballMap = new HashMap<Integer, Ball>();
	public BallCache(){
		
	}
	
	/**
	 * TODO
	 * @param ball
	 */
	public boolean putBall(Ball ball){
		int id = this.generateBallID();
		return false;
	}
	
	private int generateBallID(){
		int rand = (int) (Math.random() * Integer.MAX_VALUE);
		while(ballMap.containsKey(rand)){
			rand = (int) (Math.random() * Integer.MAX_VALUE);
		}
		return rand;
	}
	
}