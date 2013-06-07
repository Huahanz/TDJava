package Helpers;

import java.util.HashMap;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import balls.Ball;

/**
 * Map ballID to ball reference Many reference. Check multithread issues. 
 * TODO delegate
 */
public class BallCache {
	static final ConcurrentHashMap<Integer, Ball> ballMap = new ConcurrentHashMap<Integer, Ball>();
	static final Map unmodifiableMap = Collections.unmodifiableMap(ballMap);

	public BallCache() {
	}

	/**
	 * TODO
	 * @param ball
	 */
	public static int addBall(Ball ball) {
		int id = generateBallID();
		ballMap.put(id, ball);
		return id;
	}
	
	public static boolean setBall(int id, Ball ball){
		ballMap.put(id, ball);
		return true;
	}

	/**
	 * shallow copy
	 * @return
	 */
	public static Map getMapCopy(){
		HashMap map = new HashMap<Integer, Ball>();
		map.putAll(ballMap);
		return map;
	}
	
	public static Map getMap(){
		return unmodifiableMap;
	}
	
	public static Ball getBall(int id){
		return ballMap.get(id);
	}
	
	/*
	 * Atomic
	 */
	private static int generateBallID() {
		int rand = (int) (Math.random() * Integer.MAX_VALUE);
		while (ballMap.containsKey(rand)) {
			rand = (int) (Math.random() * Integer.MAX_VALUE);
		}
		return rand;
	}

}