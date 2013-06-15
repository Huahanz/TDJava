package Wrapper;

import java.util.Vector;

public class PVPPostWrapper{
	private String pvpID;
	private Vector<String> pvpPostQueue;
	private MapInfo mapPostInfo;
	
	public PVPPostWrapper(String pvpID, Vector<String> pvpPostQueue, MapInfo mapPostInfo){
		this.pvpID = pvpID;
		this.pvpPostQueue = pvpPostQueue;
		this.mapPostInfo = mapPostInfo;
	}
	
	public String getPvpID() {
		return pvpID;
	}
	
	public Vector<String> getPvpPostQueue() {
		return pvpPostQueue;
	}
	
	public Object getMapPostInfo() {
		return mapPostInfo;
	}
	
}