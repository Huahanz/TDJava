package Balls;

import Helpers.Config;

//DragonBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
//String imagePath, int attack, int scope, int maxHealth) {
public class SlowBall extends DragonBall {
	public SlowBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(id, x, y, Config.slowBallXSize, Config.slowBallYSize,
				Config.slowBallStepLength, Config.slowBallImagePath,
				Config.slowBallAttack, Config.slowBallScope, 
				Config.slowBallMaxHealth);
	}
}
