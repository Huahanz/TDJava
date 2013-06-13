package worker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import Data.QueueManager;
import Helpers.GameAux;
import Helpers.GameManager;
import Helpers.LogHelper;
import balls.ActiveBallRunnable;
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

	/**
	 * TODO Flush post updates every time switch pvp. 
	 */
	public Object start() {
		int currentMapID= Scheduler.getNextMap();
		ArrayList pvpUpdates = this.loadNextPVPUpdates(currentMapID);
		Object rst = this.parseAndExe(pvpUpdates);
		//start delegation
		this.invokeBallThreads();
		return rst;
	}

	private void invokeBallThreads() {
		//monitor seq during queue insertion. Add condition to stop these threads. 
		//trim to the seq size wanted, flag the outgoing queue 
		
		Thread activeBallThread = new Thread(new ActiveBallRunnable());
		activeBallThread.start();

		Thread towerThread = new Thread(new TowerBallRunnable());
		towerThread.start();

		Thread bulletThread = new Thread(new BulletBallRunnable());
		bulletThread.start();
	}

	private ArrayList loadNextPVPUpdates(int mapID) {
		int currentPVPID = Scheduler.getNextPVP(mapID);
		ArrayList pvpUpdates = QueueManager.dequeueMapUpdates(currentPVPID);
		return pvpUpdates;
	}

	private Object parseAndExe(ArrayList pvpUpdates) {
		boolean flag = false;
		String type = null;
		for (Object obj : pvpUpdates) {
			ArrayList ballUpdates = (ArrayList) obj;
			// value, format : 
			if (flag) {
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
			// type
			else {
				type = (String) obj;
			}
			flag ^= flag;
		}
		return null;
	}

	/**
	 * @param ballUpdates : pvp_ball_id => {type_id => val} 
	 */
	private boolean assignBalls(ArrayList ballUpdates) {
		for(Object obj : ballUpdates){
			int pvpBallID = ballUpdates.indexOf(obj);
			LinkedTreeMap updates = (LinkedTreeMap)obj;
			Set entrySet = updates.keySet();
			Iterator it = entrySet.iterator();
			//A waste of loop, because the update normally has just on element 
			while(it.hasNext()){
				String key = (String) it.next();
				int typeID = (int) updates.get(key);
				String ballName = GameAux.parseBallIDToName(typeID);
				GameManager gmMgr = GameManager.getInstance();
				//TODO add position
				gmMgr.addRandBall(ballName, pvpBallID);
			}
		}
		return true;
	}

	//TODO
	private boolean quitBalls(ArrayList ballUpdates) {
		return false;
	}
	
	//TODO
	private boolean updateBalls(ArrayList ballUpdates) {
		return false;
	}
}