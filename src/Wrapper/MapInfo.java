package Wrapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import swingFrontEnd.GameInfo;

import balls.Ball;
import balls.BulletBall;

import Helpers.BallCache;

/**
 * Keep all the ball info. {pvp_ball_id, pos, type, extra}, should leave some space in case of the future requirement updates. 
 * Should be a rested LinkedTreeMap. 
 */
public class MapInfo
{
	private Map ballMap;
	public Vector<Ball> balls;
	public Vector<Ball> dieBalls;
	public Vector<BulletBall> bullets;
	public MapInfo(){
		ballMap= BallCache.getMapCopy();
		balls = (Vector<Ball>) GameInfo.balls.clone();
		dieBalls = (Vector<Ball>) GameInfo.dieBalls.clone();
		bullets = (Vector<BulletBall>) GameInfo.bullets.clone();
	}
	public Map getBallMap(){
		return ballMap;
	}
	public Vector<Ball> getBalls(){
		return balls;
	}
	public Vector<Ball> getDieBalls(){
		return dieBalls;
	}
	public Vector<BulletBall> getBullets(){
		return bullets;
	}
	/**
	 * The json communication needs the info passed to be arraylist or map, not an object. 
	 * If we add new fields in the future, we also need to update the following two serialize methods. 
	 */
	public Map getSerializableMapInfo(){
		return ballMap;
	}
	public Object serialize() {
		//TODO 
		return ballMap.toString();
	}
}