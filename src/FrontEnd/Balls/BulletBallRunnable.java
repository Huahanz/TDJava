package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class BulletBallRunnable extends BallRunnable implements Runnable {
	public BulletBallRunnable() {

	}

	public void run() {
		while (true) {
			for (Ball ball : GameInfo.balls) {
				if (ball instanceof BulletBall){
					boolean hit = ((BulletBall) ball).shoot();
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}