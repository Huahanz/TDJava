package FrontEnd.Balls;

import java.awt.image.BufferedImage;

import Helpers.Config;

public abstract class BulletBall extends ActiveBall {
	int x;
	int y;
	Ball target;
	String imagePath;
	int damage;

	public BulletBall(int x, int y, Ball ball, String imagePath) {
		super(x, y, 1, 1, 100, imagePath);
		this.setTarget(ball);
	}

	public abstract boolean shoot();
	public BufferedImage getImage() {
		return null;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Ball getTarget() {
		return target;
	}

	public void setTarget(Ball target) {
		this.target = target;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}