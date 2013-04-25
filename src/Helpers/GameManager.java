package Helpers;

import java.awt.event.MouseEvent;

import Controller.HttpManager;
import FrontEnd.GameInfo;
import FrontEnd.Balls.Ball;
import FrontEnd.Balls.DTowerBall;
import FrontEnd.Balls.FastBall;
import FrontEnd.Balls.ActiveBallRunnable;
import FrontEnd.Balls.HeroBall;
import FrontEnd.Balls.STowerBall;
import FrontEnd.Balls.SilverBulletBall;
import FrontEnd.Balls.SlowBall;
import FrontEnd.Balls.StalkBulletBall;

public class GameManager {
	private static GameManager gameManager = null;

	public static GameManager getInstance() {
		if (gameManager == null) {
			gameManager = new GameManager();
		}
		return gameManager;
	}

	public synchronized void addBall(String ballName, int x, int y) {
		this.addBall(ballName, x, y, null);
	}

	public synchronized void addBall(String ballName, int x, int y, Object obj0) {
		if (x >= Config.defaultOneSlotWidth || x <= 0
				|| y >= Config.defaultOneSlotHeight || y <= 0)
			return;
		Ball ball = null;
		switch (ballName) {
		case "Fast":
			ball = new FastBall(x, y);
			break;
		case "Slow":
			ball = new SlowBall(x, y);
			break;
		case "DTower":
			if (this.canBuildTower(x, y))
				ball = new DTowerBall(x, y);
			break;
		case "STower":
			if (this.canBuildTower(x, y))
				ball = new STowerBall(x, y);
			break;
		case "SilverBulletBall":
			if (obj0 != null)
				ball = new SilverBulletBall(x, y, (Ball) obj0);
			break;
		case "StalkBulletBall":
			if (obj0 != null)
				ball = new StalkBulletBall(x, y, (Ball) obj0);
			break;
		case "Wall":
			this.addWall(x, y);
			break;
		case "Cancel":
			this.cancel(x, y);
			break;
		case "Hero":
			ball = new HeroBall(x, y);
			break;
		default:
			return;
		}
		GameInfo.postMan.send("swing.add" + ballName + "Ball");
		if (ball != null) {
			GameInfo.balls.add(ball);
		}
	}

	public synchronized boolean killBall(Ball ball) {
		TestHelper.print("killing ball");
		if (GameInfo.balls.contains(ball)) {
			// GameInfo.balls.set(GameInfo.balls.indexOf(ball), null);
			GameInfo.balls.remove(ball);
		}
		return true;
	}

	public void loadServerBalls() {
		String ballsJson = HttpManager.readTestOneSlotBalls();

		// for(Ball ball : balls){
		// this.addBall(ballName, x, y);
		// }
	}

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

	public void addDrag(String buttonName, MouseEvent lastMouseClickedEvent,
			MouseEvent event) {
		int lxSlotNum = lastMouseClickedEvent.getX() / Config.slotWidth;
		int lySlotNum = lastMouseClickedEvent.getY() / Config.slotHeight;
		int xSlotNum = event.getX() / Config.slotWidth;
		int ySlotNum = event.getY() / Config.slotHeight;

		for (String towerButton : Config.towerButtons) {
			if (towerButton.equals(buttonName) || buttonName.equals("Wall")) {
				TestHelper.print("int add drag" + lxSlotNum + ", " + xSlotNum + ", " + lySlotNum + ", " + ySlotNum);
				if (lxSlotNum == xSlotNum) {
					int i = Math.min(ySlotNum, lySlotNum);
					int j = Math.max(ySlotNum, lySlotNum);
					for (; i <= j; i++) {
						this.addBall(buttonName, xSlotNum * Config.slotWidth, i * Config.slotHeight);
					}
				}else if(lySlotNum == ySlotNum){
					int i = Math.min(xSlotNum, lxSlotNum);
					int j = Math.max(xSlotNum, lxSlotNum);
					for (; i <= j; i++) {
						this.addBall(buttonName, i * Config.slotWidth, ySlotNum * Config.slotHeight);
					}
				}
				return;
			}
		}
	}
}