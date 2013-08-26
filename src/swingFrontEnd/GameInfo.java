package swingFrontEnd;

import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import Balls.Ball;
import Balls.BulletBall;

import Helpers.AuxRunnable;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.LogHelper;
import Helpers.MapData;
import Helpers.TestHelper;
import Map.ShortPath;

/**
 * TODO concurrency issues. TODO better global index. TODO reconstruct and
 * rewrite this class.
 */
public class GameInfo {
	public static SwingPanel swingPanel;
	public static Rectangle2D Bounds;

	// TODO change to concurrent Lists -- Vector.
	public static Vector<Ball> balls = new Vector<Ball>();
	public static Vector<Ball> dieBalls = new Vector<Ball>();
	public static Vector<BulletBall> bullets = new Vector<BulletBall>();

	// TODO move to another class and change type to final
	// belongs to the may static info, won't change during switches of pvps.
	public static byte[][][][] mapDir; // the shortest path move direction
	public static float[][][][] mapPath; // the shortest path length.
	public static byte[][][][] breakDir; // the shortest path move direction
	public static float[][][][] breakPath; // the shortest path length
	public static byte[][] TDDirMap;
	public static float[][] TDPathMap;
	public static String filePathPrefix = "path";
	public static ShortPath shortPathWrapper;

	private static boolean isSerialized() {
		String[] fileNames = { "mapDir", "mapPath", "breakDir", "breakPath",
				"TDDirMap", "TDPathMap" };
		for (String fileName : fileNames) {
			boolean isEmpty = isFileEmpty(filePathPrefix + fileName);
			if (isEmpty) {
				return false;
			}
		}
		return true;
	}

	private static boolean isFileEmpty(String fileName) {
		File file = new File(fileName);
		boolean empty = !file.exists() || file.length() == 0;
		return empty;
	}

	private static void deserialize() throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream(filePathPrefix + "mapDir");
		ObjectInputStream iis = new ObjectInputStream(fis);
		mapDir = (byte[][][][]) iis.readObject();

		fis = new FileInputStream(filePathPrefix + "mapPath");
		iis = new ObjectInputStream(fis);
		mapPath = (float[][][][]) iis.readObject();

		fis = new FileInputStream(filePathPrefix + "breakDir");
		iis = new ObjectInputStream(fis);
		breakDir = (byte[][][][]) iis.readObject();

		fis = new FileInputStream(filePathPrefix + "breakPath");
		iis = new ObjectInputStream(fis);
		breakPath = (float[][][][]) iis.readObject();

		fis = new FileInputStream(filePathPrefix + "TDDirMap");
		iis = new ObjectInputStream(fis);
		TDDirMap = (byte[][]) iis.readObject();

		fis = new FileInputStream(filePathPrefix + "TDPathMap");
		iis = new ObjectInputStream(fis);
		TDPathMap = (float[][]) iis.readObject();
	}

	private static void serialize() throws IOException {
		FileOutputStream fos = new FileOutputStream(filePathPrefix + "mapDir");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(mapDir);

		fos = new FileOutputStream(filePathPrefix + "mapPath");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(mapPath);

		fos = new FileOutputStream(filePathPrefix + "breakDir");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(breakDir);

		fos = new FileOutputStream(filePathPrefix + "breakPath");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(breakPath);

		fos = new FileOutputStream(filePathPrefix + "TDDirMap");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(TDDirMap);

		fos = new FileOutputStream(filePathPrefix + "TDPathMap");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(TDPathMap);
	}

	// TODO rewrite this, remove currentmap.
	// This need to change for every pvp.
	public static int[][] currentMap; // the map
	private static boolean[][][][] marks; // temp marks

	static int m = 0;
	static int n = 0;

	public static void clearBalls() {
		balls = new Vector<Ball>();
		dieBalls = new Vector<Ball>();
		bullets = new Vector<BulletBall>();
	}

	public static void clearMap() {
		currentMap = MapData.loadMap(Config.defaultTestMapNum);
	}

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

	/*
	 * TODO support multiple maps.
	 */
	public static void loadMap() throws IOException, ClassNotFoundException {
		// if not in db
		currentMap = MapData.loadMap(Config.defaultTestMapNum);
		if (!isSerialized()) {
			calculateMap();
		} else {
			LogHelper.debug("Deserialize path from file. ");
			deserialize();
		}
		m = currentMap[0].length;
		n = currentMap.length;
		TDDirMap = mapDir[n - 1][m - 1];
		TDPathMap = mapPath[n - 1][m - 1];
		shortPathWrapper = new ShortPath();
		serialize();
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