package Balls;

import Helpers.Config;

public class DTowerBall extends TowerBall {

	public DTowerBall(int id, int x, int y, int size) {
		super(id, x, y, size, Config.DTowerImagepath, Config.DTowerScope,
				Config.DTowerCost, Config.DTowerMapID, Config.DTowerBulletName);
	}
}
