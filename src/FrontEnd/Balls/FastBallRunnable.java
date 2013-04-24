package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class FastBallRunnable extends BallRunnable implements Runnable {

	public FastBallRunnable() {
	}

	public void run() {
		while (true) {
			for(Ball ball : GameInfo.balls){
				if(ball instanceof FastBall)
					((FastBall) ball).moveToExit();
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}