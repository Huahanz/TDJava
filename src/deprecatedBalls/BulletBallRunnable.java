package deprecatedBalls;

import Helpers.LogHelper;
import swingFrontEnd.GameInfo;

public class BulletBallRunnable extends BallRunnable {
	public BulletBallRunnable() {

	}

	public void job() {
		for (int i = 0; i < GameInfo.bullets.size(); i++) {
			Ball ball = GameInfo.bullets.get(i);
			if (ball instanceof BulletBall) {
				boolean hit = ((BulletBall) ball).shoot();
			}
		}
	}

}