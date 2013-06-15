package Request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.internal.LinkedTreeMap;

import Wrapper.InWrapper;
import Wrapper.Parser;

import Controller.HttpManager;
import Data.QueueManager;
import Helpers.LogHelper;

/**
 * Assume single thread can enter this class, didn't consider the multithread situation. 
 * The only reference is in Requester.java
 * TODO add logs. 
 */
public class RequestManager {
	public RequestManager() {

	}

	public static void main(String[] args){
		RequestManager r = new RequestManager();
		r.makeRequest(0);
	}
	/**
	 * We assume for every round, the size of update on the map is small. So we can wait for the json parsing.  
	 */
	private LinkedTreeMap getUpdatesByMap(int mapID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("map_id", String.valueOf(mapID)));
		String postURL = this
				.getByMapPostUrl("pvp", "PVPCtrl", "pop_recent_updates");
		String json = HttpManager.sendPostRequest(formparams, postURL);
		Object obj = Parser.parseIn(json);
		if(obj == null || obj instanceof String){
			return null;
		}
		LinkedTreeMap rst = (LinkedTreeMap)obj; 
		LogHelper.debug("requester received " + json);
		return rst;
	}

	private String getByMapPostUrl(String dir, String className, String method) {
		return dir + ":" + className + ":" + method;
	}

	private boolean addMapUpdates(int mapID, LinkedTreeMap mapUpdates){
		if(mapUpdates == null){
			return false;
		}
		LogHelper.debug("requester enqueue updates " + mapUpdates.toString());
		return QueueManager.enqueueMapUpdates(mapID, mapUpdates);
	}
	
	public boolean makeRequest(int mapID){
		ArrayList pvpList = new ArrayList<Integer>();
		LinkedTreeMap mapUpdates = this.getUpdatesByMap(mapID);
		if(mapUpdates == null){
			return false;
		}
		return this.addMapUpdates(mapID, mapUpdates);		
	}
}