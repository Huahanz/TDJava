package FrontEnd.Balls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.ImageHelper;
import Helpers.MapData;

public abstract class TowerBall extends Ball {

	protected int xSlotNum;
	protected int ySlotNum;
	protected int size;
	static int mapID;

	public TowerBall(int x, int y, int size){
		this(x/Config.slotWidth, y/Config.slotHeight, size, null);
	}
//	public TowerBall(int xSlotNum, int ySlotNum, int size) {
//		this(xSlotNum, ySlotNum, size, "");
//	}

	public TowerBall(int xSlotNum, int ySlotNum, int size, String imagePath) {
		super(xSlotNum * Config.slotWidth, ySlotNum * Config.slotHeight,
				Config.slotWidth * size, Config.slotHeight * size, imagePath);
		this.xSlotNum = xSlotNum;
		this.ySlotNum = ySlotNum;
		this.setMapID(mapID);
		drawTower();
	}

	public void drawTower() {
		GameInfo.currentMap[xSlotNum][ySlotNum] = this.getMapID();
	}

	public BufferedImage getImage() {
		if (this.getImagePath() == null)
			return null;
		if (this.image == null) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(this
						.getImagePath()));
				this.image = ImageHelper.resizeImage(40, 40, originalImage,
						originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return this.image;
	}

	public boolean defend(){
		return false;
	}
	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}

}