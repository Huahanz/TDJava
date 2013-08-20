package deprecatedBalls;

import Helpers.Config;
import Helpers.GameManager;

public class FastBulletBall extends BulletBall {

	int damage = 4;

	public FastBulletBall(int id, int x, int y, int XSIZE, int YSIZE, int stepLength,
			Ball ball, String imagePath) {
		super(id, x, y, XSIZE, YSIZE, stepLength, ball, imagePath);
	}

	public boolean shoot() {
		Ball target = this.getTarget();
		if (target instanceof DragonBall) {
			((ActiveBall) target).setHealth(((ActiveBall) target).getHealth()
					- this.getDamage());
			if (((ActiveBall) target).getHealth() <= 0) {
				target.setImagePath(Config.DieImagePath);
				GameManager gm = GameManager.getInstance();
				gm.killBall(target, false);
				gm.killBullet(this);
			}
		}
		return true;
	}
}