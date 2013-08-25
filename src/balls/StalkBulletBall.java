package Balls;

import Helpers.Config;

public class StalkBulletBall extends BulletBall {

	public StalkBulletBall(int id, int x, int y, int XSIZE, int YSIZE,
			int stepLength, Ball ball, String imagePath) {
		super(id, x, y, XSIZE, YSIZE, Config.stalkBulletStepLength, ball,
				imagePath, Config.stalkBulletAttack);
	}

}