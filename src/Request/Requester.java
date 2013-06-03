package Request;

import Helpers.Config;

public class Requester implements Runnable
{

	public void run() {
		int[] mapList = Config.mapList;
		RequestManager requestManager = new RequestManager();
		for(int mapID : mapList){
			requestManager.makeRequest(mapID);
		}
	}
	
}