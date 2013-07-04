package worker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

import swingFrontEnd.GameInfo;
import Data.QueueManager;
import Helpers.BallCache;
import Helpers.GameAux;
import Helpers.GameManager;
import Helpers.LogHelper;
import Model.PVPStore;
import Send.SendWrapper;
import Wrapper.MapInfo;
import balls.ActiveBallRunnable;
import balls.BallRunnable;
import balls.BulletBallRunnable;
import balls.TowerBallRunnable;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Only one reference(int Main.java). No multithread issue.
 * 
 */
public class Executor {

	public Executor() {

	}

	
	public Object start(){
		
		int currentMapID = Scheduler.getNextMap();
		String currentPVPID = Scheduler.getNextPVP(currentMapID);
				
		//clear every thing : seqNum. 
		SendWrapper.resetSeqNum();
		QueueManager.initPVPPostQueue();
		
		//restore everty thing. MapInfo. 
		PVPStore.restorePVP(currentPVPID);
		
		//start exe
		LogHelper.debug("start EXE on " + currentPVPID);
		ArrayList pvpUpdates = QueueManager.dequeueMapUpdates(currentPVPID);
		Object rst = this.parseAndExe(pvpUpdates);
		this.invokeBallThreads();	
		this.waitThreads();
		LogHelper.debug("exe awake");
		
		//backup everything
		MapInfo mapInfo = new MapInfo();
		PVPStore.putPVPStore(currentPVPID, mapInfo);
		QueueManager.backupPVPPostWrapper(currentPVPID, mapInfo);
		
		//teardown every thing
		this.teardown();
		return rst;
	}
	
	private void teardown() {	
		BallCache.clear();
		GameInfo.clearBalls();
		GameInfo.clearMap();
	}

	private void waitThreads(){
		try {
			BallRunnable.barrier.await();
			LogHelper.debug("after the first barrier wait");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO imp threadpool
	 */
	static ActiveBallRunnable activeBallRunnable = new ActiveBallRunnable();
	static TowerBallRunnable towerBallRunnable = new TowerBallRunnable();
	static BulletBallRunnable bulletBallRunnable = new BulletBallRunnable();
	static Thread activeBallThread = new Thread(activeBallRunnable);
	static Thread towerThread = new Thread(towerBallRunnable); 
	static Thread bulletThread = new Thread(bulletBallRunnable);
	private void invokeBallThreads() {
		// monitor seq during queue insertion. Add condition to stop these
		// threads.
		// trim to the seq size wanted, flag the outgoing queue

		this.renewBallThreads();
		activeBallThread.start();
		LogHelper.debug("activeball thread start");

		towerThread.start();
		LogHelper.debug("towerball thread start");

		bulletThread.start();
		LogHelper.debug("bulletball thread start");
	}

	private void renewBallThreads(){
		activeBallRunnable = new ActiveBallRunnable();
		towerBallRunnable = new TowerBallRunnable();
		bulletBallRunnable = new BulletBallRunnable();
		activeBallThread = new Thread(activeBallRunnable);
		towerThread = new Thread(towerBallRunnable); 
		bulletThread = new Thread(bulletBallRunnable);
	}

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