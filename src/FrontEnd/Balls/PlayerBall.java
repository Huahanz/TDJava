package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.GameManager;

public abstract class PlayerBall extends ActiveBall {

	public PlayerBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}
	public boolean bruteHunt() {
		Ball target = this.findCLoset();
		if(target == null)
			return false;
		if(this.move(target))
			this.attack(target);
		return true;
	}
	public Ball findCLoset() {
		int ds = Integer.MAX_VALUE;
		Ball target = null;
		for(Ball ball : GameInfo.balls){
			if(ball instanceof DragonBall){
				int dx = Math.abs(this.getX() - ball.getX());
				int dy = Math.abs(this.getY() - ball.getY());
				if(dx + dy < ds){
					ds = dx + dy;
					target = ball;
				}
			}
		}
		return target;
	}

}