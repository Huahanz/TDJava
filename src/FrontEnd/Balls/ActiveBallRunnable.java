package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class ActiveBallRunnable extends BallRunnable implements Runnable {

	public ActiveBallRunnable() {
	}

	public void run() {
		while (true) {
			for(int i =0; i < GameInfo.balls.size(); i++){
				Ball ball = GameInfo.balls.get(i);
				if(ball instanceof FastBall)
					((FastBall) ball).moveToExit();
				else if(ball instanceof SlowBall){
					((SlowBall)ball).randomWalk();
				}else if(ball instanceof HeroBall){
					((HeroBall)ball).hunt();
				}
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}