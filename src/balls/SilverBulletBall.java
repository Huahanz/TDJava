package Balls;

import Helpers.Config;

public class SilverBulletBall extends BulletBall {

	public SilverBulletBall(int id, int x, int y, Ball ball) {
		super(id, x, y, Config.silverBulletXSize, Config.silverBulletYSize,
				Config.silverBulletStepLength, ball,
				Config.silverBulletBallImagePath, Config.silverBulletDamage);
	}
}