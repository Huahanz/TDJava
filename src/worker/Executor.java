package worker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import Data.QueueManager;
import Helpers.BallCache;
import Helpers.Config;
import Helpers.GameAux;
import Helpers.GameManager;
import Helpers.LogHelper;
import Wrapper.MapInfo;
import balls.ActiveBallRunnable;
import balls.BulletBallRunnable;
import balls.TowerBallRunnable;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Only one reference(int Main.java). No multithread issue.
 * 
 */
public class Executor {
	public static AtomicInteger count = new AtomicInteger(0);

	public Executor() {

	}

	/**
	 * TODO Flush post updates every time switch pvp.
	 */
	public Object start() {
		setup();
		count = new AtomicInteger(0);
		int currentMapID = Scheduler.getNextMap();
		// ArrayList pvpUpdates = this.loadNextPVPUpdates(currentMapID);

		String currentPVPID = Scheduler.getNextPVP(currentMapID);
		LogHelper.debug("start EXE on " + currentPVPID);

		ArrayList pvpUpdates = QueueManager.dequeueMapUpdates(currentPVPID);

		// TODO get map info
		MapInfo mapInfo = new MapInfo();
		QueueManager.switchPVPPostQueue(mapInfo, currentPVPID);
		Object rst = this.parseAndExe(pvpUpdates);
		// start delegation
		this.invokeBallThreads();
		try {
			int tmp = count.get();
			while (tmp < Config.numPerRound) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogHelper.debug("exe awake");
		return rst;
	}

	private void setup() {
		BallCache.clear();
	}

	private void invokeBallThreads() {
		// monitor seq during queue insertion. Add condition to stop these
		// threads.
		// trim to the seq size wanted, flag the outgoing queue

		Thread activeBallThread = new Thread(new ActiveBallRunnable());
		activeBallThread.start();
		LogHelper.debug("activeball thread start");

		Thread towerThread = new Thread(new TowerBallRunnable());
		towerThread.start();
		LogHelper.debug("towerball thread start");

		Thread bulletThread = new Thread(new BulletBallRunnable());
		bulletThread.start();
		LogHelper.debug("bulletball thread start");
	}

	// private ArrayList loadNextPVPUpdates(int mapID) {
	// String currentPVPID = Scheduler.getNextPVP(mapID);
	// ArrayList pvpUpdates = QueueManager.dequeueMapUpdates(currentPVPID);
	// return pvpUpdates;
	// }

	/**
	 * pvpUpdates format : { type => {field_type => field_val} }
	 */
	private Object parseAndExe(ArrayList pvpUpdates) {
		if (pvpUpdates == null)
			return null;
		boolean flag = false;
		String type = null;
		for (Object obj : pvpUpdates) {
			if (flag) {
				LinkedTreeMap ballUpdates = null;
				if (obj instanceof ArrayList) {
					ballUpdates = this.wrapToLinkedTreeMap((ArrayList) obj);
				} else {
					ballUpdates = (LinkedTreeMap) obj;
				}
				LogHelper.debug(obj.toString());
				switch (type) {
				case "assign_balls":
					this.assignBalls(ballUpdates);
					break;
				case "quit_balls":
					this.quitBalls(ballUpdates);
					break;
				case "update_balls":
					this.updateBalls(ballUpdates);
					break;
				default:
					LogHelper.error("error update format in updateQueue");
				}
			}
			else {
				type = (String) obj;
				LogHelper.debug(type);
			}
			flag = !flag;
		}
		LogHelper.debug("parsed and applied updates received from requester.");
		return null;
	}

	/**
	 * Lazy
	 */
	private LinkedTreeMap wrapToLinkedTreeMap(ArrayList obj) {
		LinkedTreeMap<String, LinkedTreeMap> rst = new LinkedTreeMap<String, LinkedTreeMap>();
		synchronized (obj) {
			for (int i = 0; i < obj.size(); i++) {
				Object val = obj.get(i);
				if(!(val instanceof LinkedTreeMap)){
					LogHelper.error("val is not linkedtreemap" + val.toString());
					continue;
				}
				rst.put(String.valueOf(i), (LinkedTreeMap)val);
			}
		}
		return rst;
	}

	/**
	 * @param ballUpdates
	 *            : pvp_ball_id => {type_id => val}
	 */
	private boolean assignBalls(LinkedTreeMap ballUpdates) {
		Set entrySet = ballUpdates.keySet();
		Iterator it = entrySet.iterator();
		// A waste of loop, because the update normally has just on element
		while (it.hasNext()) {
			String ballID = (String) it.next();
			LinkedTreeMap obj = (LinkedTreeMap) ballUpdates.get(ballID);
			Set objSet = obj.keySet();
			Iterator objIt = objSet.iterator();
			while (objIt.hasNext()) {
				String fieldType = (String) objIt.next();
				String fieldVal = (String) obj.get(fieldType);
				// A waste of loop, because the update normally has just two
				// elements
				if (fieldType.equals("type_id")) {
					String ballName = GameAux.parseBallIDToName(Integer
							.valueOf(fieldVal));
					GameManager gmMgr = GameManager.getInstance();
					// TODO add position
					gmMgr.addRandBall(ballName, Integer.parseInt(ballID));
				}
			}
		}
		return true;
	}

	// TODO
	private boolean quitBalls(LinkedTreeMap ballUpdates) {
		return false;
	}

	// TODO
	private boolean updateBalls(LinkedTreeMap ballUpdates) {
		return false;
	}
}