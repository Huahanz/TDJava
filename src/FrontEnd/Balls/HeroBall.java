package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.Config;

public class HeroBall extends DragonBall{

	int attack = 10;
	
	private HeroBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}
	private HeroBall(int x, int y){
		this(x, y, 10, 10, 13, Config.HeroBallImagePath);
	}
	public boolean hunt() {
		Ball target = null;
		for(int i =0; i < GameInfo.balls.size(); i++){
			Ball ball = GameInfo.balls.get(i);
			if(ball instanceof DragonBall && !(ball instanceof HeroBall)){
				target = ball;
			}
		}
		if(target == null)
			return false;
		boolean r = this.move(target);
		if(r)
			this.attack(target);
		return true;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
}