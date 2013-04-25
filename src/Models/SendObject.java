package Models;

import java.io.Serializable;
import java.util.ArrayList;

import FrontEnd.Balls.Ball;

public class SendObject implements Serializable{

	public ArrayList<Ball> balls;
	
	public SendObject(){
		this.balls = new ArrayList<Ball>();
	} 
	
	public void addBall(Ball ball){
		this.balls.add(ball);
	}
	
	
}
