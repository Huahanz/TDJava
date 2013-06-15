package Request;

import Helpers.Config;
import Helpers.LogHelper;

public class Requester implements Runnable {

	public void run() {
		LogHelper.debug("start runing requester");
		while (true) {
			int[] mapList = Config.mapList;
			RequestManager requestManager = new RequestManager();
			for (int mapID : mapList) {
				requestManager.makeRequest(mapID);
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}