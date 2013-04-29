package Controller;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import balls.Ball;
import Helpers.TestHelper;

public class PostMan {
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