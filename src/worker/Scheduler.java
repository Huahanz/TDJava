package worker;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Data.QueueManager;
import Helpers.Config;

/**
 * This is a static class in charge of income, outgoing and other schedulings.
 * So all the methods should be atomic. Read only to all the reference classes.  
 *
 */
public class Scheduler
{	
	/**
	 * TODO imp load balancer
	 */
	public static String getNextPVP(int mapID){
		ArrayList<String> pvpList = QueueManager.getMapPVPList(mapID);
		int size = pvpList.size();
		int ix = (int) (Math.random() * size);
		String pvpID = pvpList.get(ix);
		//only for log. 
		QueueManager.enqueuePVPScheduleQueue(pvpID);
		return pvpID;
	}
	
	public static int getNextMap(){
		int[] mapList = Config.mapList;
		int size = mapList.length;
		int ix = (int) (Math.random() * size);
		int mapID = mapList[ix];
		QueueManager.enqueueMapScheduleQueue(mapID);
		return mapID;
	}
	
}