package deprecatedBalls;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import swingFrontEnd.GameInfo;

import Helpers.Config;
import Helpers.GameManager;
import Helpers.ImageHelper;
import Helpers.TestHelper;
import Send.SendWrapper;

public abstract class ActiveBall extends Ball {
	public int health;
	public int stepLength;
	public String imagePath = null;
	public BufferedImage image = null;
	public BufferedImage healthImage = null;
	protected int attack;
	protected int scope;

	public ActiveBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(id, x, y, XIZE, YSIZE, imagePath);
		this.setStepLength(stepLength);
		this.setX(x);
		this.setY(y);
	}

	//TODO reform the structure of these move functions
	public boolean moveWithBreak(int x, int y) {
		if (this.isBlocked(x, y)) {
			return this.moveToBreakBlock(x, y);
		} else {
			return this.move(x, y);
		}
	}

	public boolean moveToBreakBlock(Ball to) {
		return this.moveToBreakBlock(to.getX(), to.getY());
	}

	public boolean moveToBreakBlock(int x, int y) {
		if (!GameInfo.isValide(x, y)
				|| !GameInfo.isValide(this.getX(), this.getY()))
			return false;
		int thisXSlot = this.getX() / Config.slotWidth;
		int thisYSlot = this.getY() / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		// if (thisXSlot == toXSlot && thisYSlot == toYSlot)
		// return breakBlock(thisXSlot, thisYSlot);

		if (GameInfo.currentMap[thisYSlot][thisXSlot] != 0) {
			this.breakBlock(thisXSlot, thisYSlot);
		}
		byte dir = GameInfo.breakDir[thisYSlot][thisXSlot][toYSlot][toXSlot];

		int step = this.getStepLength();
		if ((dir & 1) == 0)
			step >>= 1;
		if (dir == 1 || dir == 2 || dir == 8)
			this.setY(this.getY() - step);
		if (dir >= 2 && dir <= 4)
			this.setX(this.getX() + step);
		if (dir >= 4 && dir <= 6)
			this.setY(this.getY() + step);
		if (dir >= 6 && dir <= 8)
			this.setX(this.getX() - step);
		SendWrapper.sendBallMove(this, this.getX(), this.getY(), 0);
		return false;
	}

	public boolean breakBlock(int xSlot, int ySlot) {
		TestHelper.print("Breaking");
		GameInfo.currentMap[ySlot][xSlot] = 0;
		SendWrapper.sendBallAction(this, "BREAKBLOCK");
		return true;
	}

	public boolean isBlocked(Ball to) {
		return this.isBlocked(to.getX(), to.getY());
	}

	public boolean isBlocked(int x, int y) {
		int thisXSlot = this.getX() / Config.slotWidth;
		int thisYSlot = this.getY() / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		if (!GameInfo.isValide(x, y)
				|| !GameInfo.isValide(this.getX(), this.getY()))
			return false;
		if (thisXSlot == toXSlot && thisYSlot == toYSlot)
			return false;
		byte dir = GameInfo.mapDir[thisYSlot][thisXSlot][toYSlot][toXSlot];

		return dir == 0;
	}

	public boolean move(Ball to) {
		return this.move(to.getX(), to.getY());
	}

	public boolean move(int x, int y) {
		if (!GameInfo.isValide(x, y)
				|| !GameInfo.isValide(this.getX(), this.getY()))
			return false;
		int oldX = this.getX();
		int oldY = this.getY();
		int thisXSlot = oldX / Config.slotWidth;
		int thisYSlot = oldY / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		boolean isArrive = false;
		if (thisXSlot == toXSlot && thisYSlot == toYSlot)
			isArrive = moveInSlot(x, y);
		else {
			byte dir = GameInfo.mapDir[thisYSlot][thisXSlot][toYSlot][toXSlot];

			int step = this.getStepLength();
			if ((dir & 1) == 0)
				step >>= 1;
			if (dir == 1 || dir == 2 || dir == 8)
				this.setY(this.getY() - step);
			if (dir >= 2 && dir <= 4)
				this.setX(this.getX() + step);
			if (dir >= 4 && dir <= 6)
				this.setY(this.getY() + step);
			if (dir >= 6 && dir <= 8)
				this.setX(this.getX() - step);
		}
		SendWrapper.sendBallMove(this, this.getX(), this.getY(), 0);
		return isArrive;
	}

	protected boolean moveInSlot(Ball to) {
		return this.moveInSlot(to.getX(), to.getY());
	}

	protected boolean moveInSlot(int x, int y) {
		int boundMinX = 0;
		int boundMinY = 0;
		int boundMaxX = Config.defaultOneSlotWidth;
		int boundMaxY = Config.defaultOneSlotHeight;

		int dx = Math.abs(x - this.getX());
		int dy = Math.abs(y - this.getY());
		int dis = dx + dy;
		if (this.getStepLength() > dis) {
			this.setX(x);
			this.setY(y);
			return true;
		}
		double steplen = this.getStepLength();
		double ratio = steplen / dis;
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

	protected boolean isInScope(int ballX, int ballY) {
		int scope = this.getScope();
		int x = this.getX();
		int y = this.getY();
		return (Math.pow(ballX - x, 2) + Math.pow(ballY - y, 2) <= Math.pow(
				scope, 2));
	}

	public int walkWay(int n) {
		return n;
	}

	public int getHealth() {
		return health;
	}

	public synchronized void setHealth(int health) {
		this.health = health;
		SendWrapper.sendBallUpdate(this, "HEALTH", this.health);
	}

	public int getStepLength() {
		return this.stepLength;
	}

	public void setStepLength(int stepLength) {
		this.stepLength = stepLength;
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

	public void setHealthImage(BufferedImage healthImage) {
		this.healthImage = healthImage;
	}

	public boolean attack(Ball ball) {
		if (!(ball instanceof ActiveBall)) {
			return false;
		}
		SendWrapper.sendBallAction(this, "ATTACK");
		((ActiveBall) ball).setHealth(((ActiveBall) ball).getHealth()
				- this.getAttack());
		if (((ActiveBall) ball).getHealth() <= 0) {
			GameManager gm = GameManager.getInstance();
			gm.killBall(ball, false);
		}
		return false;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

}