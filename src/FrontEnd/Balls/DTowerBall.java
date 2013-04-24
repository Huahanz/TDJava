package FrontEnd.Balls;

import Helpers.Config;
import Helpers.MapData;

public class DTowerBall extends TowerBall {

	final static int mapID = 2;

	public int getMapID() {
		return mapID;
	}

	public DTowerBall(int xSlotNum, int ySlotNum, int size) {
		super(xSlotNum, ySlotNum, size);
	}

	public DTowerBall(int x, int y) {
		super(x, y, DTowerBall.mapID);
	}
	
	public boolean defend(){
		super.defend();
		return false;
	}
    
}


