package balls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helpers.Config;
import Helpers.GameManager;
import Helpers.ImageHelper;

public abstract class DragonBall extends ActiveBall {
	public static int maxHealth;

	public DragonBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
		this.setHealth(this.getMaxHealth());
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
	public BufferedImage getHealthImage() {
		if (Config.HealthBarImagePath == null)
			return null;
		try {
			BufferedImage originalImage = ImageIO.read(new File(
					Config.HealthBarImagePath));
			this.healthImage = ImageHelper.resizeImage(
					(int) (Config.ImageWidth * (Math.max(1,
							(float) this.getHealth()) / this.getMaxHealth())), 10,
					originalImage, originalImage.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.healthImage;
	}

	public abstract int getMaxHealth();
	public abstract void setMaxHealth(int maxHealth);

}