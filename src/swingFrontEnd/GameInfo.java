package swingFrontEnd;

import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import balls.ActiveBallRunnable;
import balls.Ball;
import balls.BulletBall;
import balls.BulletBallRunnable;
import balls.TowerBallRunnable;

import Helpers.AuxRunnable;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.MapData;
import Helpers.TestHelper;
/**
 * TODO concurrency issues. 
 *
 */
public class GameInfo {
	public static SwingPanel swingPanel;
	public static Rectangle2D Bounds;
	
	//TODO change to concurrent Lists.  
	public static ArrayList<Ball> balls = new ArrayList<Ball>();
	public static ArrayList<Ball> dieBalls = new ArrayList<Ball>();
	public static ArrayList<BulletBall> bullets = new ArrayList<BulletBall>();
	
	//TODO move to another class and change type to final 
	public static byte[][][][] mapDir; // the shortest path move direction
	public static float[][][][] mapPath; // the shortest path length
	public static byte[][][][] breakDir; // the shortest path move direction
	public static float[][][][] breakPath; // the shortest path length
	public static byte[][] TDDirMap;
	public static float[][] TDPathMap;
	public static int[][] currentMap; // the map
	private static boolean[][][][] marks; // temp marks
	
	static int m = 0;
	static int n = 0;

	public static boolean load(SwingFrame swingFrame) {
		swingFrame.setSize(Config.defaultWidth, Config.defaultHeight);
		GameInfo.swingPanel = new SwingPanel();
		GameInfo.Bounds = GameInfo.swingPanel.getBounds();
		swingFrame.add(GameInfo.swingPanel, BorderLayout.CENTER);
		Thread painterThread = new Thread(new PainterRunnable());
		painterThread.start();
		swingFrame.addComponents();
		return true;
	}

	public static void loadMap() {
		// if not in db
		currentMap = MapData.loadMap(Config.defaultTestMapNum);
		calculateMap();
		TDDirMap = mapDir[n - 1][m - 1];
		TDPathMap = mapPath[n - 1][m - 1];
	}

	public static void calculateTDMap() {
		calculateMap();
		TDDirMap = mapDir[n - 1][m - 1];
		TDPathMap = mapPath[n - 1][m - 1];
	}

	public static void calculateMap() {
		if (currentMap == null || currentMap.length == 0
				|| currentMap[0].length == 0)
			return;
		m = currentMap[0].length;
		n = currentMap.length;
		mapDir = new byte[n][m][n][m];
		mapPath = new float[n][m][n][m];
		breakDir = new byte[n][m][n][m];
		breakPath = new float[n][m][n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				funLazy(i, j);
				funLazyBreak(i, j);
			}
		// TestHelper.printTwoDArray(breakDir[0][0], n, m);
		// TestHelper.printTwoDArray(breakPath[0][0], n, m);
		// TestHelper.printFourDArray(maheapDir, n, m);
		// TestHelper.printTwoDArray(mapDir[0][0], n, m);
		// TestHelper.printTwoDArray(mapPath[0][0], n, m);
	}

	public static void funLazyBreak(int a, int b) {
		float[][] bp = breakPath[a][b];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				bp[i][j] = Integer.MAX_VALUE;
			}
		bp[a][b] = 0;

		boolean[][] visited = new boolean[n][m];
		while (true) {
			int mina = 0, minb = 0;
			float min = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					if (!visited[i][j])
						if (bp[i][j] < min) {
							min = bp[i][j];
							mina = i;
							minb = j;
						}
			if (min == Integer.MAX_VALUE)
				return;

			// Upper left corner is (0 ,0).
			// up = 1, upright = 2, right = 3, rightdown = 4, down = 5, downleft
			// = 6 left = 7, upleft = 8

			// from down
			if (mina - 1 >= 0) {
				if (currentMap[mina - 1][minb] == 0
						&& bp[mina - 1][minb] > bp[mina][minb]) {
					bp[mina - 1][minb] = bp[mina][minb];
					breakDir[mina - 1][minb][a][b] = 5;
				} else if (bp[mina - 1][minb] > bp[mina][minb] + 1) {
					bp[mina - 1][minb] = bp[mina][minb] + 1;
					breakDir[mina - 1][minb][a][b] = 5;
				}
			}

			// up
			if (mina + 1 < n) {
				if (currentMap[mina + 1][minb] == 0
						&& bp[mina + 1][minb] > bp[mina][minb]) {
					bp[mina + 1][minb] = bp[mina][minb];
					breakDir[mina + 1][minb][a][b] = 1;

				} else if (bp[mina + 1][minb] > bp[mina][minb] + 1) {
					bp[mina + 1][minb] = bp[mina][minb] + 1;
					breakDir[mina + 1][minb][a][b] = 1;
				}
			}

			// right
			if (minb - 1 >= 0) {
				if (currentMap[mina][minb - 1] == 0
						&& bp[mina][minb - 1] > bp[mina][minb]) {
					bp[mina][minb - 1] = bp[mina][minb];
					breakDir[mina][minb - 1][a][b] = 3;
				} else if (bp[mina][minb - 1] > bp[mina][minb] + 1) {
					bp[mina][minb - 1] = bp[mina][minb] + 1;
					breakDir[mina][minb - 1][a][b] = 3;

				}
			}

			// left
			if (minb + 1 < m) {
				if (currentMap[mina][minb + 1] == 0
						&& bp[mina][minb + 1] > bp[mina][minb]) {
					bp[mina][minb + 1] = bp[mina][minb];
					breakDir[mina][minb + 1][a][b] = 7;
				} else if (bp[mina][minb + 1] > bp[mina][minb] + 1) {
					bp[mina][minb + 1] = bp[mina][minb] + 1;
					breakDir[mina][minb + 1][a][b] = 7;
				}
			}

			// down left
			if (mina - 1 >= 0 && minb + 1 < m) {

				if (currentMap[mina - 1][minb + 1] == 0
						&& bp[mina - 1][minb + 1] > bp[mina][minb]) {
					bp[mina - 1][minb + 1] = (float) (bp[mina][minb]);
					breakDir[mina - 1][minb + 1][a][b] = 6;
				} else if (bp[mina - 1][minb + 1] > bp[mina][minb] + 1) {
					bp[mina - 1][minb + 1] = (float) (bp[mina][minb] + 1);
					breakDir[mina - 1][minb + 1][a][b] = 6;
				}
			}

			// down right
			if (mina - 1 >= 0 && minb - 1 >= 0) {
				if (currentMap[mina - 1][minb - 1] == 0
						&& bp[mina - 1][minb - 1] > bp[mina][minb]) {
					bp[mina - 1][minb - 1] = (float) (bp[mina][minb]);
					breakDir[mina - 1][minb - 1][a][b] = 4;
				} else if (bp[mina - 1][minb - 1] > bp[mina][minb] + 1) {
					bp[mina - 1][minb - 1] = (float) (bp[mina][minb] + 1);
					breakDir[mina - 1][minb - 1][a][b] = 4;
				}
			}

			// upper left
			if (mina + 1 < n && minb + 1 < m) {
				if (currentMap[mina + 1][minb + 1] == 0
						&& bp[mina + 1][minb + 1] > bp[mina][minb]) {
					bp[mina + 1][minb + 1] = (float) (bp[mina][minb]);
					breakDir[mina + 1][minb + 1][a][b] = 8;
				} else if (bp[mina + 1][minb + 1] > bp[mina][minb] + 1) {
					bp[mina + 1][minb + 1] = (float) (bp[mina][minb] + 1);
					breakDir[mina + 1][minb + 1][a][b] = 8;
				}
			}

			// upper right
			if (mina + 1 < n && minb - 1 >= 0) {
				if (currentMap[mina + 1][minb - 1] == 0
						&& bp[mina + 1][minb - 1] > bp[mina][minb]) {
					bp[mina + 1][minb - 1] = (float) (bp[mina][minb]);
					breakDir[mina + 1][minb - 1][a][b] = 2;
				} else if (bp[mina + 1][minb - 1] > bp[mina][minb] + 1) {
					bp[mina + 1][minb - 1] = (float) (bp[mina][minb] + 1);
					breakDir[mina + 1][minb - 1][a][b] = 2;
				}
			}

			visited[mina][minb] = true;
		}
	}

	public static void funLazy(int a, int b) {
		if (currentMap[a][b] == 1)
			return;
		float[][] mp = mapPath[a][b];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				mp[i][j] = Integer.MAX_VALUE;
			}
		mp[a][b] = 0;

		boolean[][] visited = new boolean[n][m];
		while (true) {
			int mina = 0, minb = 0;
			float min = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					if (currentMap[i][j] == 0 && !visited[i][j])
						if (mp[i][j] < min) {
							min = mp[i][j];
							mina = i;
							minb = j;
						}
			if (min == Integer.MAX_VALUE)
				return;

			// Upper left corner is (0 ,0).
			// up = 1, upright = 2, right = 3, rightdown = 4, down = 5, downleft
			// = 6 left = 7, upleft = 8

			// from down
			if (mina - 1 >= 0 && mp[mina - 1][minb] > mp[mina][minb] + 1
					&& currentMap[mina - 1][minb] == 0) {
				mp[mina - 1][minb] = mp[mina][minb] + 1;
				mapDir[mina - 1][minb][a][b] = 5;
			}

			// up
			if (mina + 1 < n && mp[mina + 1][minb] > mp[mina][minb] + 1
					&& currentMap[mina + 1][minb] == 0) {
				mp[mina + 1][minb] = mp[mina][minb] + 1;
				mapDir[mina + 1][minb][a][b] = 1;
			}

			// right
			if (minb - 1 >= 0 && mp[mina][minb - 1] > mp[mina][minb] + 1
					&& currentMap[mina][minb - 1] == 0) {
				mp[mina][minb - 1] = mp[mina][minb] + 1;
				mapDir[mina][minb - 1][a][b] = 3;
			}

			// left
			if (minb + 1 < m && mp[mina][minb + 1] > mp[mina][minb] + 1
					&& currentMap[mina][minb + 1] == 0) {
				mp[mina][minb + 1] = mp[mina][minb] + 1;
				mapDir[mina][minb + 1][a][b] = 7;
			}

			// down left
			if (mina - 1 >= 0 && minb + 1 < m
					&& mp[mina - 1][minb + 1] > mp[mina][minb] + 1.41
					&& currentMap[mina - 1][minb + 1] == 0
					&& currentMap[mina][minb + 1] == 0
					&& currentMap[mina - 1][minb] == 0

			) {
				mp[mina - 1][minb + 1] = (float) (mp[mina][minb] + 1.41);
				mapDir[mina - 1][minb + 1][a][b] = 6;
			}

			// down right
			if (mina - 1 >= 0 && minb - 1 >= 0
					&& mp[mina - 1][minb - 1] > mp[mina][minb] + 1.41
					&& currentMap[mina - 1][minb - 1] == 0
					&& currentMap[mina][minb - 1] == 0
					&& currentMap[mina - 1][minb] == 0) {
				mp[mina - 1][minb - 1] = (float) (mp[mina][minb] + 1.41);
				mapDir[mina - 1][minb - 1][a][b] = 4;
			}

			// upper left
			if (mina + 1 < n && minb + 1 < m
					&& mp[mina + 1][minb + 1] > mp[mina][minb] + 1.41
					&& currentMap[mina + 1][minb + 1] == 0
					&& currentMap[mina][minb + 1] == 0
					&& currentMap[mina + 1][minb] == 0) {
				mp[mina + 1][minb + 1] = (float) (mp[mina][minb] + 1.41);
				mapDir[mina + 1][minb + 1][a][b] = 8;
			}

			// upper right
			if (mina + 1 < n && minb - 1 >= 0
					&& mp[mina + 1][minb - 1] > mp[mina][minb] + 1.41
					&& currentMap[mina + 1][minb - 1] == 0
					&& currentMap[mina][minb - 1] == 0
					&& currentMap[mina + 1][minb] == 0) {
				mp[mina + 1][minb - 1] = (float) (mp[mina][minb] + 1.41);
				mapDir[mina + 1][minb - 1][a][b] = 2;
			}

			visited[mina][minb] = true;
		}
	}

	public static void startTD() {
		//GameInfo.loadMap();
		if(Config.isWallBuilt){
			GameInfo.calculateTDMap();
		}
		GameManager.getInstance().generateDragons(10);
		Thread fastBallThread = new Thread(new ActiveBallRunnable());
		fastBallThread.start();

		Thread towerThread = new Thread(new TowerBallRunnable());
		towerThread.start();

		Thread bulletThread = new Thread(new BulletBallRunnable());
		bulletThread.start();
		
//		Thread auxThread = new Thread(new AuxRunnable());
//		auxThread.start();
		Config.isWallBuilt = false;
	}

	public static void addWall() {

	}

	public static boolean isXSlotValide(int slotNum) {
		return slotNum >= 0 && slotNum < Config.slotWidthNumber;
	}

	public static boolean isYSlotValide(int slotNum) {
		return slotNum >= 0 && slotNum < Config.slotHeightNumber;
	}

	public static boolean isValide(int x, int y) {
		int xSlot = x / Config.slotWidth;
		int ySlot = y / Config.slotHeight;
		return isXSlotValide(xSlot) && isYSlotValide(ySlot);
	}
}