package Helpers;

/**
 * Many references. All function should be atomic. Read only. Static class. 
 *
 */
public class GameAux{
	
	public GameAux()
	{
		
	}
	
	public static String parseBallIDToName(int typeID){
		String[] ballNames = {"Fast", "Slow", "DTower", "STower", "SilverBullet", "StalkBullet", "Wall"};
		if(typeID >= 0 && typeID < ballNames.length){
			return ballNames[typeID];
		}
		return null;
	}

	public static int mapBallType(String ballType){
		String[] ballNames = {"Fast", "Slow", "DTower", "STower", "SilverBullet", "StalkBullet", "Wall"};
		int i = -1;
		for(String name : ballNames){
			i++;
			if(name.equals(ballType)){
				return i;
			}
		}
		return i;
	}

	public static int mapAction(String action){
		String[] actions = {};
		int i = -1; 
		for(String ele: actions){
			i++;
			if(ele.equals(actions)){
				return i;
			}
		}
		return -1;
	}

	public static int mapUpdate(String updateType) {
		String[] updates = {};
		int i = -1; 
		for(String ele: updates){
			i++;
			if(ele.equals(updateType)){
				return i;
			}
		}
		return -1;
	}
		
}