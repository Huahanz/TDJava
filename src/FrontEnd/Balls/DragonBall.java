package FrontEnd.Balls;

import Helpers.Config;
import Helpers.GameManager;

public class DragonBall extends ActiveBall {

	public DragonBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}
	public void moveToExit() {
		int thisXSlot = this.getX() / Config.slotWidth;
		int thisYSlot = this.getY() / Config.slotHeight;
		int toXSlot = (Config.defaultOneSlotWidth - Config.slotWidth)
				/ Config.slotWidth;
		int toYSlot = (Config.defaultOneSlotHeight - Config.slotHeight)
				/ Config.slotHeight;
		if (thisXSlot == toXSlot && thisYSlot == toYSlot) {
			GameManager gm = GameManager.getInstance();
			gm.reachDestination(this);
		} else {
			this.moveWithBreak(Config.defaultOneSlotWidth - Config.slotWidth,
					Config.defaultOneSlotHeight - Config.slotHeight);
		}
	}

//	public boolean attack(Ball ball) {
//		if(!(ball instanceof ActiveBall)){
//			return false;
//		}
//		((ActiveBall)ball).setHealth(((ActiveBall)ball).getHealth() - this.getAttack());
//		if(((ActiveBall)ball).getHealth() <= 0){
//			GameManager gm = GameManager.getInstance();
//			gm.killBall(ball, false);
//		}
//		return false;
//	}
	
}