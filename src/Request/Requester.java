package Request;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import Wrapper.InWrapper;
import Wrapper.Parser;

import Controller.HttpManager;
import Data.QueueManager;

public class Requester {
	public Requester() {

	}

	/**
	 * We assume for every round, the size of update on the map is small. So we can wait for the json parsing.  
	 */
	private InWrapper[] getUpdatesByMap(int mapID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("map_id", String.valueOf(mapID)));
		String postURL = this
				.getByMapPostUrl("pvp", "PVPCtrl", "pop_recent_updates");
		String json = HttpManager.sendPostRequest(formparams, postURL);
		InWrapper[] inWrapperArr = Parser.parseIn(json);
		return inWrapperArr;
	}

	private String getByMapPostUrl(String dir, String className, String method) {
		return dir + ":" + className + ":" + method;
	}

	private boolean enqueueUpdates(int pvpID, InWrapper[] inWrappers){
		 QueueManager.pushInQueue(pvpID, inWrappers);
	}
	
	public int[] makeRequest(int mapID){
		InWrapper[] = this.getUpdatesByMap(mapID);
	}
}