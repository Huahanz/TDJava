package Helpers;

import java.sql.Date;
import java.sql.Time;

import swingFrontEnd.GameInfo;

import balls.Ball;
import balls.DragonBall;


public class AuxRunnable implements Runnable {

	public AuxRunnable() {
	}

	public void run() {
		boolean flag = false;
		Time t = new Time(0);
		long l = t.getTime();
		double avg = 0;
		while (true) {
			if(Config.killDragons % 20 == 9 && !flag){
				for(int i = 0; i < GameInfo.balls.size(); i++){
					Ball ball = GameInfo.balls.get(i);
					if(ball instanceof DragonBall){
						((DragonBall) ball).setMaxHealth((int) (((DragonBall) ball).getMaxHealth() * Config.difficultyScaleUp));
						((DragonBall) ball).setHealth(((DragonBall) ball).getMaxHealth());
					}
				}
				long now = t.getTime();
				if(now-l > avg * 2){
					Config.difficultyScaleUp += 4.5;
				}else if(now - l < avg * 2){
					Config.difficultyScaleUp -= 4.5;
				}
				avg = (avg + now - l)/2;
				l = now;
				flag = true;
			}else if(Config.killDragons % 20 != 9){
				flag = false;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}