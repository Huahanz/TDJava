package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class STowerBall extends TowerBall {
	public static int mapID = 3;
	protected int scope = 26;
	protected int attack = 10;
	protected String bulletName = "StalkBulletBall";

	public STowerBall(int x, int y, int size) {
		super(x, y, size);
	}

	public STowerBall(int x, int y) {
		this(x, y, 1);
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

}