package Helpers;

public class Config
{
	public static double difficultyScaleUp = 1.2;
	public static final int TowerNumber = 1000;
	public static final int STowerBallCost = 300;
	public static final int WallCost = 25;
	public static final int HeroCost = 1;
	public static final int SoliderCost = 1000;
	public static final int DTowerBallCost = 200;
	public static final int DragonBallReward = 25;
	public static final String destinationImagePath = "Asset/Destination.png";
	public static final String scopeCircleImagePath = "Asset/scopeCircle.png";
	public static final String backgroundImagePath = "Asset/backgroundGrass.png";
	public static final String wallImagePath = "Asset/wall.png";
	public static int gold = 1000;
	public static final String HeroBallImagePath = "Asset/HeroBall.jpg";
	public static final String MapPath0 = "Asset/PurpleD.png";
	public static final String MapPath1 = "Asset/PurpleRedS.png";
	public static final String FastBallImagePath = "Asset/FastBall.gif";
	public static final String HealthBarImagePath = "Asset/HealthBar.png";
	public static final String SlowBallImagePath = "Asset/SlowBall.jpg";
	public static final String DieImagePath = "Asset/die1.jpeg";
	public static final String silverBulletBallImagePath = null;
	public static final String stalkBulletBallImagePath = null;
	public static final int defaultOneSlotWidth = 1200;
	public static final int defaultOneSlotHeight = 600;
	public static final int defaultWidth = 1400;
	public static final int defaultHeight = 800;
	public static int slotWidth = 100;
	public static int slotHeight = 60;
	public static int slotWidthNumber = 10;
	public static int slotHeightNumber = 10;
	public static final int defaultTestMapNum = 7;
	public static final String[] activeballButtons = {"Fast", "Slow", "Hero", "Solider"};
	public static final String[] towerButtons = {"STower", "DTower"};
	public static final String[] otherButtons = {"Start", "Wall", "Cancel", "Random", "Hard"};
	public static final String startButtonName = "Start";
	public static final String simulatorButtonName = "Simulator";
	public static final int DragonImageSize = 20;
	public static int lostDragon = 0;
	public static int killDragons = 0;
	public static final String SoliderBallImagePath = "Asset/SoliderBall.jpg";
	public static final int ImageWidth = 30;
	public static final int ImageHeight = 30;
	public static boolean isWallBuilt = false;
	public static final int numPerRound = 10000;
	public static final String[][] MemServers = {{"127.0.0.1","25001"}, {"127.0.0.1", "11211"}};
	/**
	 * income queue
	 */
	public static int[] mapList = {0};
}
