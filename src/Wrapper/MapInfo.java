package Wrapper;

import java.util.Map;

import Helpers.BallCache;

/**
 * Keep all the ball info. {pvp_ball_id, pos, type, extra}, should leave some space in case of the future requirement updates. 
 * Should be a rested LinkedTreeMap. 
 */
public class MapInfo
{
	Map ballMap;
	public MapInfo(){
		ballMap= BallCache.getMapCopy();
	}
}