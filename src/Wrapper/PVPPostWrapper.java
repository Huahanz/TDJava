package Wrapper;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import Helpers.LogHelper;

public class PVPPostWrapper {
	private String pvpID;
	private Vector<Integer> pvpPostQueue;
	private MapInfo mapPostInfo;
	private HashMap<Integer, Vector<Integer>> pvpPostMap;


	public PVPPostWrapper(String pvpID, Vector<Integer> pvpPostQueue,
			MapInfo mapPostInfo) {
		this.pvpID = pvpID;
		this.pvpPostQueue = pvpPostQueue;
		this.mapPostInfo = mapPostInfo;
	}

	public String getPvpID() {
		return pvpID;
	}

	/**
	 * TODO free pvpPostQueue after use. Make sure the GC can free the mem
	 * space.
	 */
	public HashMap<Integer, Vector<Integer>> transform() {
		this.pvpPostMap = new HashMap<Integer, Vector<Integer>>();
		int size = pvpPostQueue.size();
		if (size % 3 != 0) {
			LogHelper.error("PVPPOSTQUEUE size error. PVPPOSTWRAPPER. ");
		}
		for (int i = 0; i + 2 < size;) {
			int x = pvpPostQueue.get(i++);
			int pvpBallID = -x;
			int pvpPlayerID = pvpBallID >> 8;
			int y = pvpPostQueue.get(i++);
			int z = pvpPostQueue.get(i++);
			if (!pvpPostMap.containsKey(pvpPlayerID)) {
				pvpPostMap.put(pvpPlayerID, new Vector<Integer>());
			}
			Vector<Integer> val = pvpPostMap.get(pvpPlayerID);
			val.add(x);
			val.add(y);
			val.add(z);
		}
		return this.getPvpPostMap();
	}

	
	public Object getMapPostInfo() {
		return mapPostInfo.serialize();
	}

	public HashMap<Integer, Vector<Integer>> getPvpPostMap() {
		return pvpPostMap;
	}

}