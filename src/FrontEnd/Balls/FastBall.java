package FrontEnd.Balls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.ImageHelper;
import Helpers.TestHelper;

public class FastBall extends DragonBall {
	public int health = 100;
	public int stepLength = 10;

	public FastBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public FastBall(int x, int y) {
		this(x, y, 12, 15, 10, Config.FastBallImagePath);
	}

	public FastBall() {
		this(0, 0);
	}

	public void randomWalk() {
		for (int i = 0; i < GameInfo.balls.size(); i++) {
			Ball ball = GameInfo.balls.get(i);
			if (ball instanceof SlowBall) {
				if (!this.isBlocked(ball)) {
					this.move(ball);
					return;
				} else {
					this.moveToBreakBlock(ball);
				}
			}
		}
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStepLength() {
		return stepLength;
	}

	public void setStepLength(int stepLength) {
		this.stepLength = stepLength;
	}

}