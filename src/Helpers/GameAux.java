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
	
}