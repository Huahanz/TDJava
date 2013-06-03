package Tests.Simulate;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import Controller.HttpManager;
import Helpers.FilterHelper;
import Wrapper.Parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

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
		System.out.println("assignball : " + json);
		return null;
	}

	protected Object joinPVP(String playerID, String pvpID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("player_id", playerID));
		formparams.add(new BasicNameValuePair("pvp_id", pvpID));
		String json = HttpManager.requestController(formparams, "map",
				"MapCtrl", "join_pvp_map");
		System.out.println("joinpvp : " + json);
		LinkedTreeMap obj = Parser.parseIn(json);
		return (String) (obj.get("pvp_id"));
	}

	protected Object createPVP(String playerID, String mapID) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("player_id", playerID));
		formparams.add(new BasicNameValuePair("map_id", mapID));
		String json = HttpManager.requestController(formparams, "map",
				"MapCtrl", "load_or_create_map");
		System.out.println("createPVP" + json);
		Gson gson = new Gson();
		LinkedTreeMap obj = Parser.parseIn(json);
		Object pvpID = obj.get("pvp_id");
		// String pvpID = (String) (obj.get("pvp_id"));
		return pvpID;
	}

	protected Object createrPlayer(String udid) {
		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("udid", udid));
		String json = HttpManager.requestController(formparams, "player",
				"PlayerCtrl", "add_player");
		System.out.println("createPlayer" + json);
		Gson gson = new Gson();
		LinkedTreeMap obj = Parser.parseIn(json);
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
		System.out.println("addBalls" + json);
		LinkedTreeMap obj = Parser.parseIn(json);
		ArrayList<String> ballIDs = (ArrayList<String>) obj.get("ball_ids");
		return ballIDs;
	}
}