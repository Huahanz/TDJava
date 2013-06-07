package Send;

import balls.ActiveBall;
import balls.Ball;

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
	 * 
	 */

	public SendWrapper() {

	}

	public static void sendAddBall(Ball ball, String ballType) {
		
	}
	
	public static void sendBallMove(Ball ball, int x, int y, int z){
		
	}
	
	public static void sendBallAction(Ball ball, String action){
		
	}

	public static void addWall(int x, int y) {
		
	}

	public static void removeWall(int x, int y) {
		
	}

	public static void sendBallUpdate(Ball ball, String type,
			int newVal) {
		
	}
	
	/**
	 * 
	 * @param pvp_ball_id
	 * @param seq_num
	 * @param type
	 * @param payload
	 * @return
	 */
	private Object pack(int pvp_ball_id, int seq_num, int type, String payload) {
//		ArrayList<BasicNameValuePair> pack = new ArrayList<BasicNameValuePair>();
//		pack.add(new BasicNameValuePair("ball_name", ball.getClass().getName()));
//		pack.add(new BasicNameValuePair("ball_x", String.valueOf(ball.getX())));
//		pack.add(new BasicNameValuePair("ball_y", String.valueOf(ball.getY())));
		return null;
	}



	
	// pack
	// add to queue
	
}