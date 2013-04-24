package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class TowerBallRunnable extends BallRunnable implements Runnable {

	public TowerBallRunnable() {
	}

	public void run() {
		while (true) {
			for(Ball ball : GameInfo.balls){
				if(ball instanceof TowerBall)
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