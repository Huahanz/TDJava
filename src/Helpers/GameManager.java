package Helpers;

import Controller.HttpManager;
import FrontEnd.GameInfo;
import FrontEnd.Balls.Ball;
import FrontEnd.Balls.DTowerBall;
import FrontEnd.Balls.FastBall;
import FrontEnd.Balls.ActiveBallRunnable;
import FrontEnd.Balls.SilverBulletBall;
import FrontEnd.Balls.SlowBall;

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
		case "SilverBulletBall":
			if (obj0 != null)
				ball = new SilverBulletBall(x, y, (Ball) obj0);
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
			//GameInfo.balls.set(GameInfo.balls.indexOf(ball), null);
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
}