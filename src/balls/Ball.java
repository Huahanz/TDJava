package balls;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import swingFrontEnd.GameInfo;

import Helpers.BallCache;
import Helpers.Config;
import Helpers.GameAux;

/**
 * Update to monitor pattern with synchronization. 
 *
 */
public abstract class Ball {
	public final int id;
	public int x;
	public int y;
	public int XSIZE;
	public int YSIZE;
	public String imagePath = null;
	public BufferedImage image = null;

	public Ball(int id, int x, int y, int XSIZE, int YSIZE, String imagePath) {
		this.x = x;
		this.y = y;
		this.XSIZE = XSIZE;
		this.YSIZE = YSIZE;
		this.setImagePath(imagePath);
		BallCache.addBall(id, this);
		this.id = id;
	}

	public String toString(){
		String className = this.getClass().toString();
		return String.valueOf(GameAux.mapFullBallType(className));
	}

	public int getId() {
		return id;
	}

	public int getXSIZE() {
		return XSIZE;
	}

	public void setXSIZE(int xSIZE) {
		XSIZE = xSIZE;
	}

	public int getYSIZE() {
		return YSIZE;
	}

	public void setYSIZE(int ySIZE) {
		YSIZE = ySIZE;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Object getShape() {
		return new Ellipse2D.Double(getX()-Config.DragonImageSize, getY() - Config.DragonImageSize, XSIZE, YSIZE);
	}

	public abstract BufferedImage getImage();

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}