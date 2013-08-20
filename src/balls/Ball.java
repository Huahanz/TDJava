package Balls;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import Helpers.BallCache;
import Helpers.Config;
import Helpers.GameAux;

public abstract class Ball
{
	public Ball(int id, int x, int y, int XSIZE, int YSIZE, String imagePath) {
		this.x = new AtomicInteger(x);
		this.y = new AtomicInteger(y);
		this.XSIZE = XSIZE;
		this.YSIZE = YSIZE;
		this.imagePath = imagePath;
		BallCache.addBall(id, this);
		this.id = id;
	}
	
	public String toString(){
		String className = this.getClass().toString();
		return String.valueOf(GameAux.mapFullBallType(className));
	}
	
	public Object getShape() {
		return new Ellipse2D.Double(this.x.get()-Config.DragonImageSize, this.y.get() - Config.DragonImageSize, XSIZE, YSIZE);
	}
	
	/**
	 * The following four functions are just used for convenience. 
	 */
	public int getX(){
		return this.x.get();
	}
	
	public int getY(){
		return this.y.get();
	}
	
	public int setX(int x){
		this.x.getAndSet(x);
		return this.x.get();
	}
	
	public int setY(int y){
		this.y.getAndSet(y);
		return this.y.get();
	}
	
	public int addAndGetX(int delta){
		return this.x.addAndGet(delta);
	}

	public int addAndGetY(int delta){
		return this.y.addAndGet(delta);
	}
	
	public final int id;
	/*
	 * The reason we separate x and y instead of using a pos function to update them together is that
	 * we treat x and y as two independent properties, which actually should be.  
	 */
	private AtomicInteger x;
	private AtomicInteger y;
	public int XSIZE;
	public int YSIZE;
	public String imagePath = null;
	public BufferedImage image = null;
}