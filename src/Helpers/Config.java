package Helpers;

public class Config
{
	public static double difficultyScaleUp = 1.2;
	public static final int TowerNumber = 1000;
	public static final int STowerBallCost = 300;
	public static final int WallCost = 25;
	public static final int HeroCost = 1;
	public static final int SoliderCost = 1000;
	public static final int DragonBallReward = 25;
	public static final String destinationImagePath = "Asset/Destination.png";
	public static final String scopeCircleImagePath = "Asset/scopeCircle.png";
	public static final String backgroundImagePath = "Asset/backgroundGrass.png";
	public static final String wallImagePath = "Asset/wall.png";
	public static int gold = 1000;
	public static final String HeroBallImagePath = "Asset/HeroBall.jpg";
	public static final String MapPath0 = "Asset/PurpleD.png";
	public static final String MapPath1 = "Asset/PurpleRedS.png";
	public static final String HealthBarImagePath = "Asset/HealthBar.png";
	public static final String DieImagePath = "Asset/die1.jpeg";
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
	public static final int numPerRound = 50;
	public static final String[][] MemServers = {{"127.0.0.1","25001"}, {"127.0.0.1", "11211"}};
	public static int ballThreadWait = 300;
	/**
	 * income queue
	 */
	public static int[] mapList = {0};

	/**
	 * New global vars
	 */	
	public static final int mapWidth = 1200;
	public static final int mapHeight = 600;

	/**
	 * Static data. Load from db. 
	 */
	public static int undieHealth = Integer.MAX_VALUE/2;
	public static int bulletBallHealth = undieHealth / 1000;

	/**
	 * BulletBall
	 */
	public static int bulletBallScope = 0;

	/**
	 * SilverBullet
	 */
	public static int silverBulletXSize = 5;
	public static int silverBulletYSize = 5;
	public static int silverBulletStepLength = 300;
	public static int silverBulletDamage = 2;
	public static String silverBulletBallImagePath = null;

	/**
	 * StalkBullet
	 */
	public static int stalkBulletXSize = 5;
	public static int stalkBulletYSize = 5;
	public static int stalkBulletStepLength = 50;
	public static int stalkBulletAttack = 5;
	public static String stalkBulletBallImagePath = null;

	/**
	 * Tower
	 */
	public static int towerBallAttack = 0;

	/**
	 * DTower
	 */
	public static int DTowerAttack = 10;
	public static int DTowerScope = 300;
	public static int DTowerCost = 200;
	public static int DTowerMapID = 20;
	public static String DTowerBulletName = "SilverBulletBall";
	public static String DTowerImagepath = "";
	
	/**
	 * NTower
	 */
	public static int NTowerAttack = 4;
	public static int NTowerScope = 26;
	public static int NTowerCost = 2000;
	public static int NTowerMapID = 40;
	public static String NTowerBulletName = "FastBulletBall";
	public static String NTowerImagepath = "";
	
	/**
	 * FastBall
	 */
	public static int fastBallMaxHealth = 100;
	public static int fastBallStepLength = 20;
	public static int fastBallXSize = 12;
	public static int fastBallYSize = 15;
	public static String fastBallImagePath = "Asset/FastBall.gif";
	public static int fastBallAttack = 20;
	public static int fastBallScope = 30;
	
	/**
	 * SlowBall
	 */
	public static int slowBallMaxHealth = 300;
	public static int slowBallStepLength = 10;
	public static int slowBallXSize = 10;
	public static int slowBallYSize = 10;
	public static String slowBallImagePath = "Asset/SlowBall.jpg";
	public static int slowBallScope = 20;
	public static int slowBallAttack = 20;
	
	/**
	 * Hero Ball
	 */
	public static int heroBallAttack = 90;
	public static int heroBallScope = 30;
	public static int heroBallHealth = 5000;
	public static int heroBallXSize= 10;
	public static int heroBallYSize= 10;
	public static int heroBallStepLength= 13;
	
}
