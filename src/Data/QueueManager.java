package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.internal.LinkedTreeMap;

import Send.SendWrapper;
import Wrapper.InWrapper;

/*
 * Manage two group of queues : income queues and outgoing queues.
 * Static class. All fields should be synchronized. 
 * Many references. 
 */
public class QueueManager {
//	private static QueueManager instance = null;
	private static HashMap<Integer, LinkedTreeMap> incomeQueue = new HashMap<Integer, LinkedTreeMap>();
	private static HashMap<Integer, ArrayList> incomePVPListMap = new HashMap<Integer, ArrayList>();
	private QueueManager() {

	}

//	public static QueueManager getInstance() {
//		if (instance == null) {
//			synchronized(instance){
//				instance = new QueueManager();
//			}
//		}
//		return instance;
//	}

	public static boolean enqueueMapUpdates(int mapID, LinkedTreeMap mapUpdates)
	{
		incomeQueue.put(mapID, mapUpdates);
		Set entrySet = incomeQueue.keySet();
		Iterator it = entrySet.iterator();
		ArrayList<Integer> pvpList = new ArrayList<Integer>();
		while(it.hasNext()){
			int pvpID = Integer.parseInt((String)it.next());
			pvpList.add(pvpID);
		}
		incomePVPListMap.put(mapID, pvpList);
		return true;
	}
	public static LinkedTreeMap dequeueMapUpdates(int mapID){
	    LinkedTreeMap rst = incomeQueue.get(mapID);
	    incomeQueue.remove(mapID);
	    return rst;
	}
	public static LinkedTreeMap peekMapUpdates(int mapID){
		return incomeQueue.get(mapID);
	}
	public static ArrayList<Integer> getMapPVPList(int mapID){
		return incomePVPListMap.get(mapID);
	}
	
	
	/*
	 * XXX decrepit
	 * @var: pvp_id
	 * 
	 * @return boolean
	 */
	public boolean pushOutQueue(int pvpID, SendWrapper outWrapper) {
		return true;
	}

	public static boolean pushInQueue(int pvpID, ArrayList inWrapper) {
		
		return true;
	}

	public InWrapper popInQueue(int pvpID) {
		return null;
	}

	public SendWrapper popOutQueue(int pvpID) {
		return null;
	}

	public InWrapper peekInQueue(int pvpID) {
		return null;
	}

	public SendWrapper peekOutQueue(int pvpID) {
		return null;
	}

	public int sizeOfInQueue(int pvpID) {
		return 0;
	}

	public int sizeOfOutQueue(int pvpID) {
		return 0;
	}
}