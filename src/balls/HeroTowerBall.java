package balls;

import swingFrontEnd.GameInfo;

public class HeroTowerBall extends TowerBall{

	public static Ball target;
	public static int mapID = 50;
	public int cost = 100000;
	public HeroTowerBall(int x, int y, int size) {
		super(x, y, size);
	}

	public void drawTower() {
		GameInfo.currentMap[ySlotNum][xSlotNum] = HeroTowerBall.mapID;
	}

	public int getMapID() {
		return HeroTowerBall.mapID;
	}

	public void setMapID(int mapID) {
		HeroTowerBall.mapID = mapID;
	}

	public int getCost() {
		return this.cost;
	}
	
}