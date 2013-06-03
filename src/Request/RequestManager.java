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

	/**
	 * We assume for every round, the size of update on the map is small. So we can wait for the json parsing.  
	 */
	private LinkedTreeMap getUpdatesByMap(int mapID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("map_id", String.valueOf(mapID)));
		String postURL = this
				.getByMapPostUrl("pvp", "PVPCtrl", "pop_recent_updates");
		String json = HttpManager.sendPostRequest(formparams, postURL);
		LinkedTreeMap rst = Parser.parseIn(json);
		return rst;
	}

	private String getByMapPostUrl(String dir, String className, String method) {
		return dir + ":" + className + ":" + method;
	}

	private boolean addMapUpdates(int mapID, LinkedTreeMap mapUpdates){
		return QueueManager.enqueueMapUpdates(mapID, mapUpdates);
	}
	
	public boolean makeRequest(int mapID){
		ArrayList pvpList = new ArrayList<Integer>();
		LinkedTreeMap mapUpdates = this.getUpdatesByMap(mapID);
		return this.addMapUpdates(mapID, mapUpdates);		
	}
}