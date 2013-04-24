package Helpers;

import Controller.HttpManager;
import FrontEnd.GameInfo;
import FrontEnd.Balls.Ball;
import FrontEnd.Balls.DTowerBall;
import FrontEnd.Balls.FastBall;
import FrontEnd.Balls.FastBallRunnable;
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
			int xslot = x/Config.slotWidth;
			int yslow = y/Config.slotHeight;
			ball = new DTowerBall(x, y);
			break;
		default:
			return;
		}
		GameInfo.postMan.send("swing.add" + ballName + "Ball");
		if (ball != null) {
			GameInfo.balls.add(ball);
		}
	}
	
	public void loadServerBalls(){
		String ballsJson = HttpManager.readTestOneSlotBalls();
		
//		for(Ball ball : balls){
//			this.addBall(ballName, x, y);
//		}
	}
}