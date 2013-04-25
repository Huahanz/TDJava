package FrontEnd.Balls;

import FrontEnd.GameInfo;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.MapData;
import Helpers.TestHelper;

public class DTowerBall extends TowerBall {

	final static int mapID = 2;
	protected int scope = 26;
	protected int attack = 10;
	protected String bulletName = "SilverBulletBall";

	public DTowerBall(int xSlotNum, int ySlotNum, int size) {
		super(xSlotNum, ySlotNum, size);
	}

	public DTowerBall(int x, int y) {
		super(x, y, 1);
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

}
