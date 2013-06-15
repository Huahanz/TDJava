package Simulator;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import Controller.HttpManager;
import Helpers.FilterHelper;
import Helpers.LogHelper;
import Wrapper.Parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Can only get access from here. No concurrency issue.
 * 
 */
public class Simulator {

	public Simulator() {

	}

	public void setupPVPForwardQueue() {
		// create player
		// add balls
		int playerSize = 10;
		String mapID = "0";
		String[] udids = new String[playerSize];
		String[] playerIDs = new String[playerSize];
		int randBase = Math.max(playerSize,
				(int) (Math.random() * Integer.MAX_VALUE));
		String[] balls = { "0", "1", "1" };
		String pvpID = null;
		for (int i = 0; i < playerSize; i++) {
			udids[i] = String.valueOf(randBase - i);
			playerIDs[i] = (String) this.createrPlayer(udids[i]);
			ArrayList<String> ballIDs = (ArrayList<String>) this.addBalls(
					playerIDs[i], balls);
			if (i % 5 == 0) {
				pvpID = (String) this.createPVP(playerIDs[i], mapID);
			} else {
				this.joinPVP(playerIDs[i], pvpID);
			}
			ballIDs = FilterHelper.randFilterArrayList(ballIDs, 1);
			this.assignBalls(ballIDs, playerIDs[i], pvpID);
		}
		LogHelper.debug("finish seting up forward queue");
	}

	protected Object assignBalls(ArrayList<String> ballIDs, String playerID,
			String pvpID) {
		Gson gson = new Gson();
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		String jsonBallIDs = gson.toJson(ballIDs);
		formparams.add(new BasicNameValuePair("json_ball_ids", jsonBallIDs));
		formparams.add(new BasicNameValuePair("player_id", playerID));
		formparams.add(new BasicNameValuePair("pvp_id", pvpID));
		String json = HttpManager.requestController(formparams, "pvp",
				"PVPCtrl", "assign_balls");
		LogHelper.info("assignball : " + json);
		return null;
	}

	protected Object joinPVP(String playerID, String pvpID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("player_id", playerID));
		formparams.add(new BasicNameValuePair("pvp_id", pvpID));
		String json = HttpManager.requestController(formparams, "map",
				"MapCtrl", "join_pvp_map");
		LogHelper.info("joinpvp : " + json);
		LinkedTreeMap obj = (LinkedTreeMap) Parser.parseIn(json);
		return (String) (obj.get("pvp_id"));
	}

	protected Object createPVP(String playerID, String mapID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("player_id", playerID));
		formparams.add(new BasicNameValuePair("map_id", mapID));
		String json = HttpManager.requestController(formparams, "map",
				"MapCtrl", "load_or_create_map");
		LogHelper.info("createPVP" + json);
		Gson gson = new Gson();
		LinkedTreeMap obj = (LinkedTreeMap) Parser.parseIn(json);
		Object pvpID = obj.get("pvp_id");
		// String pvpID = (String) (obj.get("pvp_id"));
		return pvpID;
	}

	protected Object createrPlayer(String udid) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("udid", udid));
		String json = HttpManager.requestController(formparams, "player",
				"PlayerCtrl", "add_player");
		LogHelper.info("createPlayer" + json);
		Gson gson = new Gson();
		LinkedTreeMap obj = (LinkedTreeMap) Parser.parseIn(json);
		String playerID = (String) obj.get("player_id");
		return playerID;
	}

	protected Object addBalls(String playerID, String[] ballTypeIDs) {
		Gson gson = new Gson();
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("player_id", playerID));
		formparams.add(new BasicNameValuePair("json_type_ids", gson
				.toJson(ballTypeIDs)));
		String json = HttpManager.requestController(formparams, "ball",
				"BallCtrl", "add_balls");
		LogHelper.info("addBalls" + json);
		LinkedTreeMap obj = (LinkedTreeMap) Parser.parseIn(json);
		ArrayList<String> ballIDs = (ArrayList<String>) obj.get("ball_ids");
		return ballIDs;
	}

	public static void clearData() {
		HttpManager.requestController(new ArrayList<BasicNameValuePair>(),
				"tests", "AuxCtrl", "clear_db");
		LogHelper.debug("cleared db");
		HttpManager.requestController(new ArrayList<BasicNameValuePair>(),
				"tests", "AuxCtrl", "clear_mem");
		LogHelper.debug("cleared mem");
	}
}