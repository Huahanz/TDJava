package deprecatedBalls;

import swingFrontEnd.GameInfo;
import Helpers.BallCache;
import Helpers.Config;

public class HeroBall extends PlayerBall {

	int attack = 10;
	static int targetX;
	static int targetY;
	static HeroBall instance = null;

	private HeroBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(id, x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	private HeroBall(int id, int x, int y) {
		this(id, x, y, 10, 10, 13, Config.HeroBallImagePath);
	}

	public static HeroBall getInstance(int x, int y) {
		if (instance == null) {
			int id = BallCache.generateBallID();
			instance = new HeroBall(id, x, y);
		}
		return instance;
	}

	public boolean move() {
		if (super.move(targetX, targetY)) {
			this.bruteHunt();
			return true;
		}
		return false;
	}

	public boolean setTarget(int x, int y) {
		HeroBall.targetX = x;
		HeroBall.targetY = y;
		return true;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
}