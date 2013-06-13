package balls;

import swingFrontEnd.GameInfo;

public class STowerBall extends TowerBall {
	public static int mapID = 30;
	protected int scope = 400;
	protected int attack = 10;
	protected String bulletName = "StalkBulletBall";
	protected int cost = 500;
	public STowerBall(int id, int x, int y, int size) {
		super(id, x, y, size);
	}

	public STowerBall(int id, int x, int y) {
		this(id, x, y, 1);
	}

	public void drawTower() {
		GameInfo.currentMap[ySlotNum][xSlotNum] = STowerBall.mapID;
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
		return STowerBall.mapID;
	}

	public void setMapID(int mapID) {
		STowerBall.mapID = mapID;
	}

	public int getCost() {
		return this.cost;
	}

}