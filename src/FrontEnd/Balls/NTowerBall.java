package FrontEnd.Balls;

import FrontEnd.GameInfo;

public class NTowerBall extends TowerBall{
	public static int mapID = 40;
	protected int scope = 26;
	protected int attack = 4;
	protected String bulletName = "FastBulletBall";
	public NTowerBall(int x, int y, int size) {
		super(x, y, size);
	}
	public NTowerBall(int x, int y){
		this(x, y, 1);
	}
	public void drawTower() {
		GameInfo.currentMap[ySlotNum][xSlotNum] = NTowerBall.mapID;
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
		return NTowerBall.mapID;
	}
	public void setMapID(int mapID) {
		NTowerBall.mapID = mapID;
	}
}