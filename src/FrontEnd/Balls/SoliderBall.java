package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.Config;

public class SoliderBall extends PlayerBall {

	int attack = 10;
	int scope = 1000;

	public SoliderBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public SoliderBall(int x, int y) {
		this(x, y, 10, 10, 13, Config.SoliderBallImagePath);
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

}