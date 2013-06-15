package balls;

import Helpers.LogHelper;
import swingFrontEnd.GameInfo;

public class TowerBallRunnable extends BallRunnable {

	public TowerBallRunnable() {
	}

	public void job() {
		for (int i = 0; i < GameInfo.balls.size(); i++) {
			Ball ball = GameInfo.balls.get(i);
			if (ball instanceof TowerBall)
				((TowerBall) ball).defend();
		}
	}
}