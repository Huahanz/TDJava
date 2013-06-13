package Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import Send.SendWrapper;
import Wrapper.InWrapper;

import com.google.gson.internal.LinkedTreeMap;

/*
 * Manage three group of queues : income queues, outgoing queues and schedule history.
 * Static class. All fields should be synchronized. 
 * Many references. 
 */
public class QueueManager {

	/**
	 * For the income queues.
	 */

	private final static Map<Integer, ArrayList> pvpQueues = new ConcurrentHashMap<Integer, ArrayList>();
	private final static Map unmodifiablePVPMap = Collections
			.unmodifiableMap(pvpQueues);
	private static HashMap<Integer, ArrayList> pvpListMap = new HashMap<Integer, ArrayList>();
	private final static Map unmodifiablePVPListMap = Collections
			.unmodifiableMap(pvpListMap);

	private QueueManager() {

	}

	/**
	 * Should have only one reference in RequesterManager.
	 */
	public static boolean enqueueMapUpdates(int mapID, LinkedTreeMap mapUpdates) {
		Set entrySet = mapUpdates.keySet();
		Iterator it = entrySet.iterator();
		ArrayList<Integer> pvpList = new ArrayList<Integer>();
		while (it.hasNext()) {
			int pvpID = Integer.parseInt((String) it.next());
			pvpList.add(pvpID);
			ArrayList val = (ArrayList) mapUpdates.get(pvpID);
			pvpQueues.put(pvpID, val);
		}
		pvpListMap.put(mapID, pvpList);
		return true;
	}

	/**
	 * Should have only one reference in Executor, so don't add synchronize
	 * block.
	 */
	public static ArrayList dequeueMapUpdates(int pvpID) {
		if (!pvpQueues.containsKey(pvpID)) {
			return null;
		}
		ArrayList rst = pvpQueues.get(pvpID);
		pvpQueues.remove(pvpID);
		return rst;
	}

	public static ArrayList<Integer> getMapPVPList(int mapID) {
		return pvpListMap.get(mapID);
	}

	/**
	 * For the post queues. 
	 */
	private final static Vector<String> pvpPostQueue = new Vector<String>();
	public static boolean enqueuePVPPost(String val){
		pvpPostQueue.add(val);
		return true;
	}
	
	public static Vector<String> popupPVPPostQueue(){
		synchronized(pvpPostQueue){
			Vector<String> rst = (Vector<String>) pvpPostQueue.clone();
			pvpPostQueue.clear();
			return rst;
		}
	}
	
//	private final static Map<Integer, ArrayList> pvpPostQueues = new ConcurrentHashMap<Integer, ArrayList>();
//	private final static Map unmodifiablePVPPostQueues = Collections
//			.unmodifiableMap(pvpQueues);
//	private static HashMap<Integer, ArrayList> pvpPostQueuesList = new HashMap<Integer, ArrayList>();
//	private final static Map unmodifiablePVPPostQueuesList = Collections
//			.unmodifiableMap(pvpListMap);
//
//	public static boolean enqueuePVPPostQueues(int pvpID, String val) {
//		
//	}
//
//	public static String dequeuePVPPostQueues(int pvpID) {
//
//	}

	/**
	 * For the Scheduler. FIFO
	 */

	private final static LinkedList<Integer> pvpScheduleQueue = new LinkedList<Integer>();
	private final static List<Integer> unmodifiablePVPScheduleQueue = Collections
			.unmodifiableList(pvpScheduleQueue);
	private final static LinkedList<Integer> mapScheduleQueue = new LinkedList<Integer>();
	private final static List<Integer> unmodifiableMapScheduleQueue = Collections
			.unmodifiableList(pvpScheduleQueue);

	public static boolean enqueuePVPScheduleQueue(int pvpID) {
		pvpScheduleQueue.addLast(pvpID);
		return true;
	}

	public static int dequeuePVPScheduleQueue() {
		int rst = pvpScheduleQueue.pollFirst();
		return rst;
	}

	public static int peekPVPScheduleQueue() {
		int rst = pvpScheduleQueue.peekFirst();
		return rst;
	}
	
	public static boolean enqueueMapScheduleQueue(int mapID) {
		mapScheduleQueue.addLast(mapID);
		return true;
	}

	public static int dequeueMapScheduleQueue() {
		int rst = mapScheduleQueue.pollFirst();
		return rst;
	}
	
	public static int peekMapScheduleQueue() {
		int rst = mapScheduleQueue.peekFirst();
		return rst;
	}
	
}