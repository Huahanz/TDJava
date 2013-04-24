package FrontEnd.Balls;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.ImageHelper;
import Helpers.TestHelper;

public abstract class ActiveBall extends Ball {
	public int health;
	public int stepLength;
	public int x;
	public int y;
	public String imagePath = null;
	public BufferedImage image = null;

	public ActiveBall(int x, int y, int XIZE, int YSIZE, int stepLength, String imagePath) {
		super(x, y, XIZE, YSIZE, imagePath);
		this.setStepLength(stepLength);
		this.setX(x);
		this.setY(y);
	}

	public boolean move(Ball to) {
		return this.move(to.getX(), to.getY());
	}
	
	public boolean move(int x, int y) {
		int thisXSlot = this.getX() / Config.slotWidth;
		int thisYSlot = this.getY() / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		if (thisXSlot == toXSlot & thisYSlot == toYSlot)
			return moveInSlot(x, y);
		byte dir = GameInfo.mapDir[thisYSlot][thisXSlot][toYSlot][toXSlot];
		
		int step = this.getStepLength();
		if((dir & 1) == 0)
			step >>= 1;
		if (dir == 1 || dir == 2 || dir == 8)
			this.setY(this.getY() - step);
		if (dir >= 2 &&  dir <= 4)
			this.setX(this.getX() + step);
		if (dir >= 4 && dir <= 6)
			this.setY(this.getY() + step);
		if (dir >= 6 && dir <= 8)
			this.setX(this.getX() - step);
		return false;
	}
	
	public boolean moveInSlot(Ball to){
		return this.moveInSlot(to.getX(), to.getY());
	}

	public boolean moveInSlot(int x, int y){
		int boundMinX = 0;
		int boundMinY = 0;
		int boundMaxX = Config.defaultOneSlotWidth;
		int boundMaxY = Config.defaultOneSlotHeight;

		int dx = Math.abs(x - this.getX());
		int dy = Math.abs(y- this.getY());
		int dis = dx + dy;
		if (this.getStepLength() > dis) {
			this.setX(x);
			this.setY(y);
			return true;
		}
		double ratio = this.getStepLength() / dis;

		if (dx != 0) {
			dy = (int) (dy * ratio);
			if (this.getY() > y) {
				dy = -dy;
			}
		}
		if (dy != 0) {
			dx = (int) (dx * ratio);
			if (this.getX() > x) {
				dx = -dx;
			}
		}
		this.setX(this.getX() + this.walkWay(dx));
		this.setY(this.getY() + this.walkWay(dy));

		if (this.getX() < boundMinX) {
			this.setX((int) boundMinX);

		}
		if (this.getX() + this.getXSIZE() >= boundMaxX) {
			this.setX((int) boundMaxX - this.getXSIZE());

		}
		if (this.getY() < boundMinY) {
			this.setY((int) boundMinY);

		}
		if (this.getY() + this.getYSIZE() >= boundMaxY) {
			this.setY((int) boundMaxY - this.getYSIZE());
		}

		return false;
	}
	public int walkWay(int n) {
		return n;
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

	public BufferedImage getImage(){
		if(this.getImagePath() == null)
			return null;
		if(this.image == null){
			try {
				BufferedImage originalImage = ImageIO.read(new File(this.getImagePath()));
				this.image = ImageHelper.resizeImage(40, 40, originalImage, originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return this.image;
	}

}