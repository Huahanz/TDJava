package Balls;

import Helpers.Config;

public class HeroBall extends PlayerBall {


	private HeroBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(id, x, y, XIZE, YSIZE, stepLength, imagePath,
				Config.heroBallAttack, Config.heroBallScope,
				Config.heroBallHealth);
	}

}