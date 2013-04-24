package FrontEnd.Balls;

import Helpers.Config;
import Helpers.GameManager;
import Helpers.MapData;
import Helpers.TestHelper;

public class DTowerBall extends TowerBall {

	final static int mapID = 2;
	protected int scope = 3;
	protected int attack = 10;
	protected String bulletName = "SilverBulletBall";

	public DTowerBall(int xSlotNum, int ySlotNum, int size) {
		super(xSlotNum, ySlotNum, size);
	}

	public DTowerBall(int x, int y) {
		super(x, y, DTowerBall.mapID);
		TestHelper.print(Config.slotWidth + " || " + Config.slotHeight + " "
				+ x + " " + y);
	}

	public boolean defend() {
		return super.defend();
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
		return mapID;
	}

}
