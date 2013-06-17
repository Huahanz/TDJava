package Send;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import Helpers.GameAux;
import Helpers.LogHelper;
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
	
	private static int getAndIncSeqNum(){
		return baseSeqNum.incrementAndGet();
	}
	
	public static int checkSeqNum(){
		return baseSeqNum.get();
	}
	
	public static void resetSeqNum(){
		baseSeqNum = new AtomicInteger(0);
	}
	
	public static void sendBallMove(Ball ball, int x, int y, int z){
		int pvpBallID = ball.getId();
		int seq = getAndIncSeqNum();
		int type = 0;
		int payload = ((x << 16) + (y << 8) + z);
		pack(pvpBallID, seq, type, payload);
	}
	
	public static void sendBallAction(Ball ball, String action){
		int pvpBallID = ball.getId();
		int seq = getAndIncSeqNum();
		int type = 1;
		int payload = GameAux.mapAction(action);
		pack(pvpBallID, seq, type, payload);
	}

	public static void sendBallUpdate(Ball ball, String updateType,
			int newVal) {
		int pvpBallID = ball.getId();
		int seq = getAndIncSeqNum();
		int type = 2;
		int updateNum = GameAux.mapUpdate(updateType);
		int payload = (updateNum << 16) + newVal;
		pack(pvpBallID, seq, type, payload);
	}
	
	public static void sendAddBall(Ball ball, String ballType) {
		int pvpBallID = ball.getId();
		int seq = getAndIncSeqNum();
		int type = 3;
		int payload = GameAux.mapBallType(ballType);
		pack(pvpBallID, seq, type, payload);
	}

	public static void deleteBall(Ball ball) {
		int pvpBallID = ball.getId();
		int seq = getAndIncSeqNum();
		int type = 4;
		int payload = 0;
		pack(pvpBallID, seq, type, payload);		
	}

	public static void addWall(int x, int y, int z) {
		int pvpBallID = -1;
		int seq = getAndIncSeqNum();
		int type = 5;
		int payload = ((x << 16) + (y << 8) + z);
		pack(pvpBallID, seq, type, payload);
	}

	public static void removeWall(int x, int y, int z) {
		int pvpBallID = -2;
		int seq = getAndIncSeqNum();
		int type = 6;
		int payload = ((x << 16) + (y << 8) + z);
		pack(pvpBallID, seq, type, payload);
	}	
	
	private static final Logger LOGGER = Logger.getLogger(
		    Thread.currentThread().getStackTrace()[0].getClassName() );
	
	/**
     * $pvp_ball_id(16) + $seq_num (16) + $type(8) + $payload(32) <= 80
     * $pvp_ball_id = ($player_rank << 8) + $pvp_ball_rank_in_player.
     * TODO should use three Integers. First for start_sign + pvp_ball_id, second for seq_num + type, last for payload.
     * The start_sign is to mark the first int negative.   
	 */
	private static void pack(int pvpBallID, int seqNum, int type, int payload) {
//		LogHelper.debug("packing " + pvpBallID + " " + seqNum + " " + type + " " + payload);
		int x = - pvpBallID;
		int y = (seqNum << 16) + type;
		SendManager.enqueuePost(x);
		SendManager.enqueuePost(y);
		SendManager.enqueuePost(payload);
	}

	// pack
	// add to queue
	
}