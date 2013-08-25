package Balls;

import swingFrontEnd.GameInfo;
import Helpers.GameManager;

public abstract class PlayerBall extends ActiveBall {

	public PlayerBall(int id, int x, int y, int XIZE, int YSIZE,
			int stepLength, String imagePath, int attack, int scope, int health) {
		super(id, x, y, XIZE, YSIZE, stepLength, imagePath, attack, scope, health);
	}

}