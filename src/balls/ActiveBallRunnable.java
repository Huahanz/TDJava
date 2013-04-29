package balls;

import swingFrontEnd.GameInfo;

public class ActiveBallRunnable extends BallRunnable implements Runnable {

	public ActiveBallRunnable() {
	}

	public void run() {
		while (true) {
			for(int i =0; i < GameInfo.balls.size(); i++){
				Ball ball = GameInfo.balls.get(i);
				if(ball instanceof DragonBall)
					((DragonBall) ball).moveToExit();
				else if(ball instanceof HeroBall){
					((HeroBall) ball).move();
				}else if(ball instanceof SoliderBall){
					((SoliderBall) ball).bruteHunt();
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