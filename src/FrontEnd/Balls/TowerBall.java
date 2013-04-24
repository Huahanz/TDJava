package FrontEnd.Balls;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.ImageHelper;
import Helpers.MapData;
import Helpers.TestHelper;

public abstract class TowerBall extends Ball {

	protected int xSlotNum;
	protected int ySlotNum;
	protected int size;
	protected int scope;
	protected int attack;
	protected String bulletName;
	static int mapID;

	public TowerBall(int x, int y, int size) {
		this(x / Config.slotWidth, y / Config.slotHeight, size, null);
	}

	// public TowerBall(int xSlotNum, int ySlotNum, int size) {
	// this(xSlotNum, ySlotNum, size, "");
	// }

	public TowerBall(int xSlotNum, int ySlotNum, int size, String imagePath) {
		super(xSlotNum * Config.slotWidth, ySlotNum * Config.slotHeight,
				Config.slotWidth * size, Config.slotHeight * size, imagePath);
		this.xSlotNum = xSlotNum;
		this.ySlotNum = ySlotNum;
		this.setMapID(mapID);
		drawTower();
	}

	public void drawTower() {
		GameInfo.currentMap[ySlotNum][xSlotNum] = this.getMapID();
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

	public boolean defend() {
		for(int i =0; i < GameInfo.balls.size(); i++){
			Ball ball = GameInfo.balls.get(i);
			if (ball instanceof DragonBall) {
				int ballX = ball.getX();
				int ballY = ball.getY();
				if (this.isInScope(ballX, ballY)) {
					return this.attack(ball);
				}
			}
		}
		return false;
	}

	public boolean attack(Ball ball) {
		TestHelper.print("attacking " + ball.getClass().getName() + "at "+ ball.getX() + ball.getY());
		GameManager gameManager = GameManager.getInstance();
		gameManager.addBall(this.getBulletName(), this.getX(), this.getY(), ball);
		return true;
	}

	public boolean isInScope(int ballX, int ballY) {
		int scope = this.getScope();
		int x = this.getX();
		int y = this.getY();
		return (ballX < x + Config.slotWidth * scope
				&& ballX > x - Config.slotWidth * scope
				&& ballY < y + Config.slotHeight * scope && ballY > y
				- Config.slotHeight * scope);
	}
	
	public Object getShape() {
		return new Ellipse2D.Double(getX(), getY(), 1, 1);
	}

	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}
	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	public String getBulletName() {
		return bulletName;
	}

	public void setBulletName(String bulletName) {
		this.bulletName = bulletName;
	}

}