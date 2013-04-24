package FrontEnd.Balls;

import Helpers.Config;
import Helpers.MapData;

public class DTowerBall extends TowerBall {

	final static int mapID = 2;
	protected int scope = 3;
	protected int attack = 10;

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



}


