package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class DTowerRunnable extends BallRunnable implements Runnable {

	public DTowerRunnable() {
	}

	public void run() {
		while (true) {
			for(Ball ball : GameInfo.balls){
				if(ball instanceof DTowerBall)
					((DTowerBall) ball).defend();
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}