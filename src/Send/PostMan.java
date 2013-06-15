package Send;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.internal.LinkedTreeMap;

import Controller.HttpManager;
import Data.MemcacheManager;
import Data.QueueManager;
import Helpers.Config;
import Helpers.GameAux;
import Helpers.GameManager;
import Helpers.LogHelper;
import Helpers.TestHelper;
import Request.RequestManager;
import Wrapper.PVPPostWrapper;
import Wrapper.Parser;

public class Postman implements Runnable {

	public Postman(){
	}
	
	public void run() {
		LogHelper.debug("start running postman");
		while (true) {
			boolean isEmpty = QueueManager.isPVPPostQueueEmpty();
			if(!isEmpty){
				PVPPostWrapper first = QueueManager.popupPVPPostWrapper();
				String pvpID = first.getPvpID();
				int slot = this.getWriteSlot(pvpID);
				LogHelper.debug("postman get slot" + slot);
				this.setWriteSlot(pvpID);
				//write to memcache directly. 
				MemcacheManager mem = new MemcacheManager(slot);
				
				//set pvp updates 
				LinkedTreeMap playerMap = this.getPlayers(pvpID);
				Set entrySet = playerMap.keySet();
				Iterator it = entrySet.iterator();
				//A waste of loop, because the update normally has just on element 
				while(it.hasNext()){
					String playerID = (String) it.next();
					String pvpPlayerID = (String) playerMap.get(playerID);		
					String key = this.getPVPUpdateKey(pvpID, slot, pvpPlayerID);
					LogHelper.debug("postman writing pvpupdates to mem key" + key + " val : " + first.getPvpPostQueue().toString());
					mem.set(key, first.getPvpPostQueue(), 0);
				}
				
				//set map info
				String key = this.getMapInfoKey(pvpID, slot);
				LogHelper.debug("postman writing mapinfo to mem key" + key + "val : " + first.getMapPostInfo().toString());
				mem.set(key, first.getMapPostInfo(), 0);
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//$prefix . "map_data:pvp_id:$pvp_id:slot_num:$slot_num";
	private String getMapInfoKey(String pvpID, int slot) {
	    String prefix = "PVPBackwardManager:";
	    return prefix + "map_data:pvp_id:" + pvpID + ":slot_num:" + slot;
	}

	//$prefix . "updates:pvp_id:$pvp_id:slot_num:$slot_num:pvp_player_id:$pvp_player_id";
	private String getPVPUpdateKey(String pvpID, int slot, String pvpPlayerID) {
		String prefix = "PVPBackwardManager:";
		return prefix +  "updates:pvp_id:" + pvpID + ":slot_num:" + slot + ":pvp_player_id:" + pvpPlayerID;
	}

	private int getWriteSlot(String pvpID){
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("pvp_id", pvpID));
		String postURL = this
				.getByMapPostUrl("pvp", "PVPBackwardCtrl", "get_write_slot_num");
		String json = HttpManager.sendPostRequest(formparams, postURL);
		Object obj = Parser.parseIn(json);
		if(obj == null){
			LogHelper.error("empty response from getwriteslot");
			return 0;
		}
		String rst = (String)obj;
		return Integer.valueOf(rst);
	}
	
	private boolean setWriteSlot(String pvpID){
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("pvp_id", pvpID));
		String postURL = this
				.getByMapPostUrl("pvp", "PVPBackwardCtrl", "set_write_slot_num");
		String json = HttpManager.sendPostRequest(formparams, postURL);
		Object obj = Parser.parseIn(json);
		if(obj == null){
			LogHelper.error("empty response from pvpctrl.setWriteSlot");
			return false;
		}
		String rst = (String) obj;
		return Boolean.valueOf(rst);
	}
	
	private LinkedTreeMap getPlayers(String pvpID){
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("pvp_id", pvpID));
		String postURL = this
				.getByMapPostUrl("pvp", "PVPCtrl", "get_players");
		String json = HttpManager.sendPostRequest(formparams, postURL);
		Object obj = Parser.parseIn(json);
		if(obj == null){
			LogHelper.error("empty response from pvpctrl.get_players");
			return null;
		}
		LinkedTreeMap player_id_map =  (LinkedTreeMap) obj;
		return player_id_map;
	}
	
	private String getByMapPostUrl(String dir, String className, String method) {
		return dir + ":" + className + ":" + method;
	}
	
}