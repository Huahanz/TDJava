package Model;

import swingFrontEnd.GameInfo;
import Wrapper.MapInfo;
import Data.QueueManager;
import Helpers.BallCache;
import Helpers.LogHelper;

/**
 * Only reference is in Executor. All method static
 */
public class PVPStore{
	
	//put pvp info
	public static void putPVPStore(String pvpID, MapInfo mapInfo){
		QueueManager.putMapInfo(pvpID, mapInfo);
	}
	//get pvp info
	public static void restorePVP(String pvpID){
		MapInfo mapInfo = QueueManager.getMapInfo(pvpID);
		if(mapInfo == null){
			return;
		}
		BallCache.restoreMapInfo(mapInfo);
		GameInfo.balls = mapInfo.getBalls();
		GameInfo.dieBalls = mapInfo.getDieBalls();
		GameInfo.bullets = mapInfo.getBullets();
	}

}