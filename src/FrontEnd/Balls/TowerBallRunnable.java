package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class TowerBallRunnable extends BallRunnable implements Runnable {

	public TowerBallRunnable() {
	}

	public void run() {
		while (true) {
			for (int i = 0; i < GameInfo.balls.size(); i++) {
				Ball ball = GameInfo.balls.get(i);
				if (ball instanceof TowerBall)
					((TowerBall) ball).defend();
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}