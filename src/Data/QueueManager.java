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
import java.util.concurrent.ConcurrentLinkedQueue;

import Helpers.LogHelper;
import Send.SendWrapper;
import Wrapper.InWrapper;
import Wrapper.MapInfo;
import Wrapper.PVPPostWrapper;

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

	private final static Map<String, ArrayList> pvpQueues = new ConcurrentHashMap<String, ArrayList>();
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
		ArrayList<String> pvpList = new ArrayList<String>();
		while (it.hasNext()) {
			String pvpID = (String) it.next();
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
	public static ArrayList dequeueMapUpdates(String pvpID) {
		if (!pvpQueues.containsKey(pvpID)) {
			return null;
		}
		ArrayList rst = pvpQueues.get(pvpID);
		pvpQueues.remove(pvpID);
		return rst;
	}

	public static ArrayList<String> getMapPVPList(int mapID) {
		return pvpListMap.get(mapID);
	}

	/**
	 * For the post queues. 
	 * There is no concurrent linkedhashmap in java, considering the post queue should be consumed quickly, 
	 * so we just keep the pvppostqueue as a linkedlist.  
	 */
	private static final Vector<String> pvpPostQueue = new Vector<String>();
	@SuppressWarnings("rawtypes")
	private final static ConcurrentLinkedQueue<PVPPostWrapper> pvpPostList = new ConcurrentLinkedQueue<PVPPostWrapper>();
	public static boolean enqueuePVPPost(String val){
		pvpPostQueue.add(val);
		return true;
	}
	
	public static Vector<String> switchPVPPostQueue(MapInfo mapInfo, String pvpID){
		LogHelper.debug("EXE switching queue");
		synchronized(pvpPostQueue){
			Vector<String> rst = (Vector<String>) pvpPostQueue.clone();
			pvpPostQueue.clear();
			PVPPostWrapper pvpPostWrapper = new PVPPostWrapper(pvpID, rst, mapInfo);
			pvpPostList.add(pvpPostWrapper);
			return rst;
		}
	}
	
	public static PVPPostWrapper popupPVPPostWrapper(){
		PVPPostWrapper first = pvpPostList.poll();
		return first;
	}
	
	public static PVPPostWrapper peekFirstPVPPostWrapper(){
		PVPPostWrapper first = pvpPostList.peek();
		return first;
	}
	
	public static boolean isPVPPostQueueEmpty(){
		int size = pvpPostList.size();
		return size == 0;
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

	private final static LinkedList<String> pvpScheduleQueue = new LinkedList<String>();
	private final static List<String> unmodifiablePVPScheduleQueue = Collections
			.unmodifiableList(pvpScheduleQueue);
	private final static LinkedList<Integer> mapScheduleQueue = new LinkedList<Integer>();
	private final static List<Integer> unmodifiableMapScheduleQueue = Collections
			.unmodifiableList(mapScheduleQueue);

	public static boolean enqueuePVPScheduleQueue(String pvpID) {
		pvpScheduleQueue.addLast(pvpID);
		return true;
	}

	public static String dequeuePVPScheduleQueue() {
		String rst = pvpScheduleQueue.pollFirst();
		return rst;
	}

	public static String peekPVPScheduleQueue() {
		String rst = pvpScheduleQueue.peekFirst();
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