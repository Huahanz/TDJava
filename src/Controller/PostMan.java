package Controller;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import balls.Ball;

public class PostMan {
	
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
	public String postAddBallURL = "http://localhost/~huahan/PPServer/index.php/blog/test_test_model";

	public PostMan() {

	}

	public void send(String string) {
	}

	public void sendAddBall(Ball ball) {
		String response = HttpManager.sendPostRequest(this.packBall(ball),
				this.postAddBallURL);
		System.out.println("get server response : " + response);
	}

	public ArrayList<BasicNameValuePair> packBall(Ball ball) {
		ArrayList<BasicNameValuePair> pack = new ArrayList<BasicNameValuePair>();
		pack.add(new BasicNameValuePair("ball_name", ball.getClass().getName()));
		pack.add(new BasicNameValuePair("ball_x", String.valueOf(ball.getX())));
		pack.add(new BasicNameValuePair("ball_y", String.valueOf(ball.getY())));
		return pack;
	}
}