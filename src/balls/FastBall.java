package Balls;

import Helpers.Config;

public class FastBall extends DragonBall {

	public FastBall(int id, int x, int y) {
		super(id, x, y, Config.fastBallXSize, Config.fastBallYSize,
				Config.fastBallStepLength, Config.fastBallImagePath,
				Config.fastBallAttack, Config.fastBallScope, 
				Config.fastBallMaxHealth);
	}
	
}