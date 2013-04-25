package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;

public class SilverBulletBall extends BulletBall {
	int targetX;
	int targetY;
	int damage = 8;

	public SilverBulletBall(int x, int y, Ball ball) {
		super(x, y, ball, Config.silverBulletBallImagePath);
		this.setTargetX(ball.getX());
		this.setTargetY(ball.getY());
	}
	
	public boolean shoot() {
		boolean hit = super.moveInSlot(this.getTargetX(), this.getTargetY());
		if (hit) {
			Ball target = this.getTarget();
			if (target instanceof DragonBall) {
				TestHelper.print("shooting " + target.getX() + "  " + target.getY() + " , " + this.getX() + " " + this.getY());
				((ActiveBall) target).setHealth(((ActiveBall) target) 
						.getHealth() - this.getDamage());
				if(((ActiveBall) target).getHealth() <= 0){
					target.setImagePath(Config.DieImagePath);
					GameManager gm = GameManager.getInstance();
					gm.killBall(target);
					gm.killBall(this);
				}
			}
			return true;
		}
		return false;
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