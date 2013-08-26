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
import Model.PVPStore;
import Wrapper.MapInfo;
import Wrapper.PVPPostWrapper;

import com.google.gson.internal.LinkedTreeMap;

/*
 * Manage three group of queues : income queues, outgoing queues and schedule history.
 * Static class. All fields should be synchronized. 
 * Many references. 
 * The reason we put all the queue here is we want concurrency issues solved more efficiently and in the same way. 
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
	 * For the post queues. There is no concurrent linkedhashmap in java,
	 * considering the post queue should be consumed quickly, so we just keep
	 * the pvppostqueue as a linkedlist.
	 * 
	 * TODO pvppostqueue is the bottleneck, need to speedup.
	 */
	private static String currentPVPID = null;

	public static String getCurrentPVPID() {
		return currentPVPID;
	}

	private static void setCurrentPVPID(String pvpID) {
		currentPVPID = pvpID;
	}

	private static Vector<Integer> pvpPostQueue = null;
	private static boolean switchingPVP = false;
	@SuppressWarnings("rawtypes")
	private final static ConcurrentLinkedQueue<PVPPostWrapper> pvpPostList = new ConcurrentLinkedQueue<PVPPostWrapper>();

	public static boolean enqueuePVPPost(int val) {
		if (!switchingPVP) {
			pvpPostQueue.add(val);
			return true;
		}
		return false;
	}

	/**
	 * Deprecated
	 * switch and create a new pvp queue.
	 */
	public static Vector<Integer> switchPVPPostQueue(MapInfo mapInfo,
			String pvpID) {
		LogHelper.debug("EXE switching queue");
		setCurrentPVPID(pvpID);
		if (pvpPostQueue == null) {
			pvpPostQueue = new Vector<Integer>();
			return pvpPostQueue;
		}
		synchronized (pvpPostQueue) {
			switchingPVP = true;
			Vector<Integer> rst = pvpPostQueue;
			PVPPostWrapper pvpPostWrapper = new PVPPostWrapper(pvpID, rst,
					mapInfo);
			pvpPostList.add(pvpPostWrapper);
			PVPStore.restorePVP(pvpID);
			// get new mapinfo from pvpstore to ball cache.
			pvpPostQueue = new Vector<Integer>();
			switchingPVP = false;
			return rst;
		}
	}

	public static Vector<Integer> initPVPPostQueue() {
		pvpPostQueue = new Vector<Integer>();
		return pvpPostQueue;
	}

	public static Vector<Integer> backupPVPPostWrapper(String pvpID,
			MapInfo mapInfo) {
		if (pvpPostQueue == null || pvpPostQueue.isEmpty()) {
			LogHelper.error("pvppostqueue empty");
			return null;
		}
		synchronized (pvpPostQueue) {
			switchingPVP = true;
			Vector<Integer> rst = pvpPostQueue;
			PVPPostWrapper pvpPostWrapper = new PVPPostWrapper(pvpID, rst,
					mapInfo);
			pvpPostList.add(pvpPostWrapper);
			switchingPVP = false;
			return rst;
		}
	}

	public static PVPPostWrapper popupPVPPostWrapper() {
		PVPPostWrapper first = pvpPostList.poll();
		return first;
	}

	public static PVPPostWrapper peekFirstPVPPostWrapper() {
		PVPPostWrapper first = pvpPostList.peek();
		return first;
	}

	public static boolean isPVPPostQueueEmpty() {
		int size = pvpPostList.size();
		return size == 0;
	}

	// private final static Map<Integer, ArrayList> pvpPostQueues = new
	// ConcurrentHashMap<Integer, ArrayList>();
	// private final static Map unmodifiablePVPPostQueues = Collections
	// .unmodifiableMap(pvpQueues);
	// private static HashMap<Integer, ArrayList> pvpPostQueuesList = new
	// HashMap<Integer, ArrayList>();
	// private final static Map unmodifiablePVPPostQueuesList = Collections
	// .unmodifiableMap(pvpListMap);
	//
	// public static boolean enqueuePVPPostQueues(int pvpID, String val) {
	//
	// }
	//
	// public static String dequeuePVPPostQueues(int pvpID) {
	//
	// }

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

	/**
	 * PVPStore
	 */

	private static final ConcurrentHashMap<String, MapInfo> pvpStoreMap = new ConcurrentHashMap<String, MapInfo>();

	public static void putMapInfo(String pvpID, MapInfo mapInfo) {
		pvpStoreMap.put(pvpID, mapInfo);
	}

	public static MapInfo getMapInfo(String pvpID) {
		if(!pvpStoreMap.containsKey(pvpID))
			return null;
		return pvpStoreMap.get(pvpID);
	}
}