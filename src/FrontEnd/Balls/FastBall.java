package FrontEnd.Balls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.ImageHelper;
import Helpers.TestHelper;

public class FastBall extends DragonBall {
	public int health = 80;
	public int stepLength = 60;

	public FastBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public FastBall(int x, int y) {
		this(x, y, 12, 15, 20, Config.FastBallImagePath);
	}

	public FastBall() {
		this(0, 0);
	}

	public void randomWalk() {
		for (int i = 0; i < GameInfo.balls.size(); i++) {
			Ball ball = GameInfo.balls.get(i);
			if (ball instanceof SlowBall) {
				if (!this.isBlocked(ball)) {
					TestHelper.print("not blocked");
					this.move(ball);
					return;
				} else {
					this.moveToBreakBlock(ball);
				}
			}
		}
	}

	public void moveToExit() {
		this.move(Config.defaultOneSlotWidth - 1,
				Config.defaultOneSlotHeight - 1);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		TestHelper.print("health setting to " + health);
		this.health = health;
	}
}