package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class BulletBallRunnable extends BallRunnable implements Runnable {
	public BulletBallRunnable() {

	}

	public void run() {
		while (true) {
			for(int i =0; i < GameInfo.balls.size(); i++){
				Ball ball = GameInfo.balls.get(i);
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