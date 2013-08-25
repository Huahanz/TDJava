package Balls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helpers.Config;
import Helpers.GameManager;
import Helpers.ImageHelper;

public abstract class DragonBall extends ActiveBall {
	public final int maxHealth;
	public DragonBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath, int attack, int scope, int maxHealth) {
		super(id, x, y, XIZE, YSIZE, stepLength, imagePath, attack, scope, maxHealth);
		this.maxHealth = maxHealth;
	}

	public BufferedImage getHealthImage() {
		if (Config.HealthBarImagePath == null)
			return null;
		try {
			BufferedImage originalImage = ImageIO.read(new File(
					Config.HealthBarImagePath));
			this.healthImage = ImageHelper.resizeImage(
					(int) (Config.ImageWidth * (Math.max(1,
							(float) this.getHealth()) / this.maxHealth)), 10,
					originalImage, originalImage.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.healthImage;
	}	
}