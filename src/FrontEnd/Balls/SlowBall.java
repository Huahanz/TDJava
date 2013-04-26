package FrontEnd.Balls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helpers.Config;
import Helpers.ImageHelper;
import Helpers.TestHelper;

public class SlowBall extends DragonBall {
	int health = 300;
	int stepLength = 10;
	static int maxHealth = 300;

	public SlowBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public SlowBall(int x, int y) {
		this(x, y, 10, 10, 10, Config.SlowBallImagePath);
	}

	public void randomWalk() {
	}

	public BufferedImage getImage() {
		if (this.getImagePath() == null)
			return null;
		if (this.image == null) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(this
						.getImagePath()));
				this.image = ImageHelper.resizeImage(Config.ImageWidth,
						Config.ImageHeight, originalImage,
						originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.image;
	}

	public int getStepLength() {
		return stepLength;
	}

	public void setStepLength(int stepLength) {
		this.stepLength = stepLength;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		TestHelper.print("smh " + maxHealth);
		SlowBall.maxHealth = maxHealth;
	}

}
