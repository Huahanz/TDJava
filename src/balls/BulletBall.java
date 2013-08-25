package Balls;

import Helpers.Config;

/**
 * TODO not sure if the parameter 'damage' is necessary. 
 */
public abstract class BulletBall extends ActiveBall {

	public BulletBall(int id, int x, int y, int XSIZE, int YSIZE,
			int stepLength, Ball ball, String imagePath, int attack) {
		super(id, x, y, XSIZE, YSIZE, stepLength, imagePath, attack, Config.bulletBallScope,
				Config.bulletBallHealth);
		this.setTarget(ball);
	}
}