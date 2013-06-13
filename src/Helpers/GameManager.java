package Helpers;

import java.awt.event.MouseEvent;

import swingFrontEnd.GameInfo;

import Send.SendWrapper;
import balls.ActiveBallRunnable;
import balls.Ball;
import balls.BulletBall;
import balls.DTowerBall;
import balls.DragonBall;
import balls.FastBall;
import balls.HeroBall;
import balls.STowerBall;
import balls.SilverBulletBall;
import balls.SlowBall;
import balls.SoliderBall;
import balls.StalkBulletBall;

import Controller.HttpManager;

public class GameManager {
	private static GameManager gameManager = null;

	public static GameManager getInstance() {
		if (gameManager == null) {
			synchronized (gameManager) {
				gameManager = new GameManager();
			}
		}
		return gameManager;
	}

	public synchronized Object addRandBall(String ballName, int pvpBallID){
		int width = Config.defaultOneSlotWidth;
		int height = Config.defaultHeight;
		int x = (int) (Math.random() * width);
		int y = (int) (Math.random() * height);
		this.addBall(ballName, x, y, null, pvpBallID);
		return null;
	}
	
	public synchronized void addBall(String ballName, int x, int y, int pvpBallID) {
		this.addBall(ballName, x, y, null, pvpBallID);
	}

	public synchronized void addBall(String ballName, int x, int y, Object obj0, int pvpBallID) {
		if (x >= Config.defaultOneSlotWidth || x <= 0
				|| y >= Config.defaultOneSlotHeight || y <= 0)
			return;
		y -= 24;
		Ball ball = null;
		switch (ballName) {
		case "Fast":
			if (this.canBuildBall(x, y))
				ball = new FastBall(pvpBallID,x, y);
			break;
		case "Slow":
			if (this.canBuildBall(x, y))
				ball = new SlowBall(pvpBallID, x, y);
			break;
		case "DTower":
			if (this.canBuildTower(x, y)
					&& Config.gold >= Config.DTowerBallCost) {
				ball = new DTowerBall(pvpBallID, x, y);
				Config.gold -= Config.DTowerBallCost;
			}
			break;
		case "STower":
			if (this.canBuildTower(x, y)
					&& Config.gold >= Config.STowerBallCost) {
				ball = new STowerBall(pvpBallID, x, y);
				Config.gold -= Config.STowerBallCost;
			}
			break;
		case "SilverBulletBall":
			if (obj0 != null)
				ball = new SilverBulletBall(pvpBallID, x, y, (Ball) obj0);
			break;
		case "StalkBulletBall":
			if (obj0 != null)
				ball = new StalkBulletBall(pvpBallID, x, y, (Ball) obj0);
			break;
		case "Wall":
			if (Config.gold >= Config.WallCost) {
				Config.isWallBuilt = true;
				if (this.addWall(x, y))
					Config.gold -= Config.WallCost;
			}
			SendWrapper.addWall(x, y, 0);
			break;
		case "Cancel":
			this.cancel(x, y);
			SendWrapper.removeWall(x, y, 0);
			break;
		case "Hero":
			if (Config.gold >= Config.HeroCost) {
				ball = HeroBall.getInstance(x, y);
				((HeroBall) ball).setTarget(x, y);
			}
			break;
		case "Solider":
			if (Config.gold >= Config.SoliderCost) {
				ball = new SoliderBall(pvpBallID, x, y);
				Config.gold -= Config.SoliderCost;
			}
			break;
		case "Upgrade":
			this.upgradeTower(x, y);
			break;
		default:
			return;
		}
		if (ball != null) {
			if (ball instanceof BulletBall) {
				GameInfo.bullets.add((BulletBall) ball);
			} else {
				GameInfo.balls.add(ball);
			}
			SendWrapper.sendAddBall(ball, ballName);
		}
	}

	private void upgradeTower(int x, int y) {
		int xSlot = x / Config.slotWidth;
		int ySlot = y / Config.slotHeight;

	}

	/**
	 * Delete the reference from ball list. 
	 */
	public synchronized boolean killBall(Ball ball, boolean skip) {
		if (GameInfo.balls.contains(ball)) {
			GameInfo.balls.remove(ball);
			if (!skip && ball instanceof DragonBall) {
				GameInfo.dieBalls.add(ball);
				Config.killDragons++;
			}
			if (!skip && ball instanceof DragonBall) {
				Config.gold += Config.DragonBallReward;
			}
			SendWrapper.deleteBall(ball);
		}else{
			
		}
		return true;
	}

	/**
	 * Delete the reference from bullet list 
	 */
	public synchronized boolean killBullet(BulletBall ball) {
		int ix = GameInfo.bullets.indexOf(ball);
		if (ix >= 0) {
			GameInfo.bullets.set(ix, null);
			SendWrapper.deleteBall(ball);
			return true;
		}
		return false;
	}

	/**
	 * rm the wall
	 */
	public boolean cancel(int x, int y) {
		int xSlotNum = x / Config.slotWidth;
		int ySlotNum = y / Config.slotHeight;
		int m = GameInfo.currentMap[0].length;
		int n = GameInfo.currentMap.length;
		if (xSlotNum >= m || xSlotNum < 0 || ySlotNum >= n || ySlotNum < 0)
			return false;
		if (GameInfo.currentMap[ySlotNum][xSlotNum] == 0)
			return false;
		GameInfo.currentMap[ySlotNum][xSlotNum] = 0;
		SendWrapper.removeWall(x, y, 0);
		return true;
	}

	public boolean addWall(int x, int y) {
		int xSlotNum = x / Config.slotWidth;
		int ySlotNum = y / Config.slotHeight;
		int m = GameInfo.currentMap[0].length;
		int n = GameInfo.currentMap.length;
		if (xSlotNum >= m || xSlotNum < 0 || ySlotNum >= n || ySlotNum < 0)
			return false;
		if (GameInfo.currentMap[ySlotNum][xSlotNum] != 0)
			return false;
		GameInfo.currentMap[ySlotNum][xSlotNum] = 1;
		SendWrapper.addWall(x, y, 0);
		return true;
	}

	public boolean canBuildBall(int x, int y) {
		int xSlotNum = x / Config.slotWidth;
		int ySlotNum = y / Config.slotHeight;
		int m = GameInfo.currentMap[0].length;
		int n = GameInfo.currentMap.length;
		if (xSlotNum >= m || xSlotNum < 0 || ySlotNum >= n || ySlotNum < 0)
			return false;
		if (GameInfo.currentMap[ySlotNum][xSlotNum] != 0)
			return false;
		return true;

	}

	public boolean canBuildTower(int x, int y) {
		int xSlotNum = x / Config.slotWidth;
		int ySlotNum = y / Config.slotHeight;
		int m = GameInfo.currentMap[0].length;
		int n = GameInfo.currentMap.length;
		if (xSlotNum >= m || xSlotNum < 0 || ySlotNum >= n || ySlotNum < 0)
			return false;
		if (GameInfo.currentMap[ySlotNum][xSlotNum] != 1)
			return false;
		return true;
	}

	public synchronized boolean reachDestination(DragonBall fastBall) {
		SendWrapper.sendBallAction(fastBall, "REACHDEST");	
		this.killBall(fastBall, true);
		Config.lostDragon++;
		return true;
	}

	public void hardGenerateDragons(int fastNum, int slowNum, int mNum) {
		for (int i = 0; i < fastNum; i++) {
			int id = BallCache.generateBallID();
			this.addBall("Fast", 1, 25, id);
		}
		for (int i = 0; i < slowNum; i++) {
			int id = BallCache.generateBallID();
			this.addBall("Slow", 1, 25, id);
		}
	}

	public void generateDragons(int num) {
		for (int i = 0; i < num; i++) {
			double r = Math.random();
			String buttonName = (r > 0.5) ? "Fast" : "Slow";
			int id = BallCache.generateBallID();
			this.addBall(buttonName, 1, 25, id);
		}
	}

	public void randomeGenerateDragons(int num) {
		for (int i = 0; i < num; i++) {
			double r = Math.random();
			String buttonName = (r > 0.5) ? "Fast" : "Slow";
			int rx = (int) (Math.random() * Config.defaultOneSlotWidth);
			int ry = (int) (Math.random() * Config.defaultOneSlotHeight);
			int id = BallCache.generateBallID();
			this.addBall(buttonName, rx, ry + 25, id);
		}

	}

}