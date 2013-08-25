package Balls;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import Data.LocalCache;
import Data.LocalCacheNode;
import Data.UnstackLocalCacheNode;
import Helpers.BallCache;
import Helpers.Config;
import Helpers.GameAux;
import Helpers.ImageHelper;

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
	
	protected String getCacheKey(Object key){
		return "Ball:" + this.getClass().toString() + ":BallID:" +  this.id + ":Key" + key.toString();  
	}
	
	protected LocalCacheNode wrapLocalCacheNode(Object key, Object val, int expiration){
		return new UnstackLocalCacheNode(this.getCacheKey(key), this, expiration);
	}
	
	public boolean putLocalCache(Object key, Object val, int expiration){
		LocalCacheNode localCacheNode = this.wrapLocalCacheNode(this.getCacheKey(key), val, expiration);
		return LocalCache.put(key, localCacheNode);
	}
	
	public Object getLocalCache(Object key){
		return LocalCache.get(this.getCacheKey(key));
	}
	
	public BufferedImage getImage() {
		if (this.imagePath == null)
			return null;
		if (this.image == null) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(this.imagePath));
				this.image = ImageHelper.resizeImage(Config.ImageWidth,
						Config.ImageHeight, originalImage,
						originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.image;
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