package Send;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import Data.QueueManager;
import Helpers.GameAux;
import Helpers.TestHelper;
import balls.ActiveBall;
import balls.Ball;

/**
 * Thread safe so far. Just wrap the update. 
 *
 */
public class SendWrapper
{
	/**
	 * $pvp_id + compressed_data
     *   $pvp_ball_id(16) + $seq_num (16) + $type(8) + $payload(32) <= 80
     *   0  move : del_x(8) + del_y(8) + del_z(8)
     *   1  action : action_num(8)
     *   2  update : info(32)
     *   3  add : ball_type(8) + info(24)
     *   4  delete : null
	 *   5  add_wall : del_x(8) + del_y(8) + del_z(8) (pvp_ball_id = -1)
	 *   6  rm_wall : del_x(8) + del_y(8) + del_z(8) (pvp_ball_id = -2)
	 */

	public SendWrapper() {

	}

	private static AtomicInteger baseSeqNum = new AtomicInteger(0);
	
	private static int getSeqNum(){
		return baseSeqNum.incrementAndGet();
	}
	
	public static void sendBallMove(Ball ball, int x, int y, int z){
		int pvpBallID = ball.getId();
		int seq = getSeqNum();
		int type = 0;
		int payload = ((x << 16) + (y << 8) + z);
		pack(pvpBallID, seq, type, payload);
	}
	
	public static void sendBallAction(Ball ball, String action){
		int pvpBallID = ball.getId();
		int seq = getSeqNum();
		int type = 1;
		int payload = GameAux.mapAction(action);
		pack(pvpBallID, seq, type, payload);
	}

	public static void sendBallUpdate(Ball ball, String updateType,
			int newVal) {
		int pvpBallID = ball.getId();
		int seq = getSeqNum();
		int type = 2;
		int updateNum = GameAux.mapUpdate(updateType);
		int payload = (updateNum << 16) + newVal;
		pack(pvpBallID, seq, type, payload);
	}
	
	public static void sendAddBall(Ball ball, String ballType) {
		int pvpBallID = ball.getId();
		int seq = getSeqNum();
		int type = 3;
		int payload = GameAux.mapBallType(ballType);
		pack(pvpBallID, seq, type, payload);
	}

	public static void deleteBall(Ball ball) {
		int pvpBallID = ball.getId();
		int seq = getSeqNum();
		int type = 4;
		int payload = 0;
		pack(pvpBallID, seq, type, payload);		
	}

	public static void addWall(int x, int y, int z) {
		int pvpBallID = -1;
		int seq = getSeqNum();
		int type = 5;
		int payload = ((x << 16) + (y << 8) + z);
		pack(pvpBallID, seq, type, payload);
	}

	public static void removeWall(int x, int y, int z) {
		int pvpBallID = -2;
		int seq = getSeqNum();
		int type = 6;
		int payload = ((x << 16) + (y << 8) + z);
		pack(pvpBallID, seq, type, payload);
	}	
	
	private static final Logger LOGGER = Logger.getLogger(
		    Thread.currentThread().getStackTrace()[0].getClassName() );
	
	/**
	 * $pvp_id + compressed_data
     * $pvp_ball_id(16) + $seq_num (16) + $type(8) + $payload(32) <= 80
	 *
	 */
	private static Object pack(int pvpBallID, int seqNum, int type, int payload) {
		int x = (pvpBallID << 16) + seqNum;
		Byte y = (byte) type;
		String sx = String.valueOf(x);
		String sy = String.valueOf(y);
		String sz = String.valueOf(payload);
		String rst = sx + sy + sz;
		TestHelper.print(rst);
		LOGGER.info(rst);
		SendManager.enqueuePost(rst);
		return rst;
	}

	// pack
	// add to queue
	
}