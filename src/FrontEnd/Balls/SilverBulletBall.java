package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;

public class SilverBulletBall extends BulletBall {

	int x;
	int y;
	int targetX;
	int targetY;
	int damage = 60;

	public SilverBulletBall(int x, int y, Ball ball) {
		super(x, y, ball, Config.silverBulletBallImagePath);
		this.setTargetX(ball.getX());
		this.setTargetY(ball.getY());
	}
	
	public boolean shoot() {
		boolean hit = super.moveInSlot(this.getTargetX(), this.getTargetY());
		if (hit) {
			Ball target = this.getTarget();
			if (target instanceof ActiveBall) {
				TestHelper.print("shooting");
				((ActiveBall) target).setHealth(((ActiveBall) target)
						.getHealth() - this.getDamage());
				if(((ActiveBall) target).getHealth() <= 0){
					//GameInfo.balls.remove(target);
					GameManager gm = GameManager.getInstance();
					gm.killBall(target);
				}
			}
			GameManager gm = GameManager.getInstance();
			gm.killBall(this);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTargetX() {
		return targetX;
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}