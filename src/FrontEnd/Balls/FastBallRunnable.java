package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class FastBallRunnable extends BallRunnable implements Runnable {

	public FastBallRunnable() {
	}

	public void run() {
		while (true) {
			for(Ball ball : GameInfo.balls){
				if(ball instanceof FastBall)
					((FastBall) ball).randomWalk();
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}