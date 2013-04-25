package FrontEnd.Balls;

import Helpers.GameManager;

public class DragonBall extends ActiveBall {

	protected int attack;

	public DragonBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public boolean attack(Ball ball) {
		if(!(ball instanceof ActiveBall)){
			return false;
		}
		((ActiveBall)ball).setHealth(((ActiveBall)ball).getHealth() - this.getAttack());
		if(((ActiveBall)ball).getHealth() <= 0){
			GameManager gm = GameManager.getInstance();
			gm.killBall(ball);
		}
		return false;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
}