package worker;

import java.util.ArrayList;

import com.google.gson.internal.LinkedTreeMap;

import Data.QueueManager;
import Helpers.Config;

/**
 * This is a static class in charge of income, outgoing and other scheduling.
 * So all the methods should be atomic. Read only to all the reference classes.  
 *
 */
public class Scheduler
{
	public static int getNextInPVP(int mapID){
		ArrayList<Integer> pvpList = QueueManager.getMapPVPList(mapID);
		int size = pvpList.size();
		int ix = (int) (Math.random() * size);
		return pvpList.get(ix);
	}
	
	public static int getNextInMap(){
		int[] mapList = Config.mapList;
		int size = mapList.length;
		int ix = (int) (Math.random() * size);
		return mapList[ix];
	}
}