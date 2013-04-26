package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.MapData;
import Helpers.TestHelper;

public class DTowerBall extends TowerBall {

	static int mapID = 20;
	protected int scope = 300;
	protected int attack = 10;
	protected String bulletName = "SilverBulletBall";
	protected int cost = 200;
	
	public DTowerBall(int xSlotNum, int ySlotNum, int size) {
		super(xSlotNum, ySlotNum, size);
	}

	public DTowerBall(int x, int y) {
		super(x, y, 1);
	}
	public boolean attack(Ball ball) {
		int angle = this.calculateAngle(this.getX(), this.getY(), ball.getX(), ball.getY());
		GameInfo.currentMap[this.getY()/Config.slotHeight][this.getX()/Config.slotWidth] = this.getMapID() + angle;
		GameManager gameManager = GameManager.getInstance();
		gameManager.addBall(this.getBulletName(), this.getX(), this.getY(),
				ball);
		return true;
	}

	public boolean defend() {
		return super.defend();
	}
	public void drawTower() {
		GameInfo.currentMap[ySlotNum][xSlotNum] = DTowerBall.mapID;
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

	public int getMapID() {
		return DTowerBall.mapID;
	}

	public void setMapID(int mapID) {
		DTowerBall.mapID = mapID;
	}

	public int getCost() {
		return this.cost;
	}

}
