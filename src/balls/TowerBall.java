package Balls;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import swingFrontEnd.GameInfo;
import Helpers.Config;
import Helpers.ImageHelper;

public abstract class TowerBall extends AtomBall {

	public int createFlag = 0;
	protected int size;
	public final String bulletName;
	public final int cost;
	public final int mapID;

	public TowerBall(int id, int x, int y, int size, String imagePath,
			int scope, int cost, int mapID, String bulletName) {
		super(id, x, y, Config.slotWidth * size, Config.slotHeight * size,
				imagePath, scope);
		this.bulletName = bulletName;
		this.cost = cost;
		this.mapID = mapID;
		drawTower();
	}

	// TODO fix this.
	public void drawTower() {
		int xSlotNum = this.getX() / Config.slotWidth;
		int ySlotNum = this.getY() / Config.slotHeight;
		GameInfo.currentMap[ySlotNum][xSlotNum] = this.mapID;
	}

	public BufferedImage getImage() {
		if (this.imagePath == null)
			return null;
		if (this.image == null) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(
						this.imagePath));
				this.image = ImageHelper.resizeImage(Config.ImageWidth,
						Config.ImageHeight, originalImage,
						originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.image;
	}
}