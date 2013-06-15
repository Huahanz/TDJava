package balls;

import Helpers.Config;
import Helpers.LogHelper;
import swingFrontEnd.GameInfo;
import worker.Executor;

public class ActiveBallRunnable extends BallRunnable{

	public ActiveBallRunnable() {
	}

	public void job(){
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
	}
}