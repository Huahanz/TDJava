package balls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import swingFrontEnd.GameInfo;

import Helpers.Config;
import Helpers.GameManager;
import Helpers.ImageHelper;
import Helpers.TestHelper;

public class FastBall extends DragonBall {
	public static int maxHealth = 100;
	public int stepLength = 20;

	private FastBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(id, x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public FastBall(int id, int x, int y) {
		this(id, x, y, 12, 15, 20, Config.FastBallImagePath);
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

	public int getStepLength() {
		return stepLength;
	}

	public void setStepLength(int stepLength) {
		this.stepLength = stepLength;
	}
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		TestHelper.print("smh " + maxHealth);
		FastBall.maxHealth = maxHealth;
	}


}