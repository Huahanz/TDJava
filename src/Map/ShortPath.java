package Map;

import java.io.Serializable;

import swingFrontEnd.GameInfo;

public class ShortPath implements Serializable{
	
	public static byte[][][][] mapDir; // the shortest path move direction 
	public static float[][][][] mapPath; // the shortest path length. 
	public static byte[][][][] breakDir; // the shortest path move direction
	public static float[][][][] breakPath; // the shortest path length
	public static byte[][] TDDirMap;
	public static float[][] TDPathMap;
	
	public ShortPath(){
		mapDir = GameInfo.mapDir;
		mapPath = GameInfo.mapPath;
		breakDir = GameInfo.breakDir;
		breakPath = GameInfo.breakPath;
		TDDirMap = GameInfo.TDDirMap;
		TDPathMap = GameInfo.TDPathMap;
	}
	
	public String toString(){
		Object[] arr = new Object[6];
		arr[0] = mapDir;
		arr[1] = mapPath;
		arr[2] = breakDir;
		arr[3] = breakPath;
		arr[4] = TDDirMap;
		arr[5] = TDPathMap;
		return arr.toString();
	}
}