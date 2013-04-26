package FrontEnd.Balls;

import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;

public class StalkBulletBall extends BulletBall {

	int damage = 5;

	public StalkBulletBall(int x, int y, Ball ball) {
		this(x, y, 5, 5, 50, ball, Config.stalkBulletBallImagePath);
	}

	public StalkBulletBall(int x, int y, int XSIZE, int YSIZE, int stepLength,
			Ball ball, String imagePath) {
		super(x, y, XSIZE, YSIZE, stepLength, ball, imagePath);
	}

	public boolean shoot() {
		boolean hit = this.moveInSlot(this.getTarget());
		if (hit) {
			Ball target = this.getTarget();
			if (target instanceof DragonBall) {
				((ActiveBall) target).setHealth(((ActiveBall) target)
						.getHealth() - this.getDamage());
				if (((ActiveBall) target).getHealth() <= 0) {
					target.setImagePath(Config.DieImagePath);
					GameManager gm = GameManager.getInstance();
					gm.killBall(target, false);
					gm.killBullet(this);
				}
			}
			return true;
		}
		return false;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}