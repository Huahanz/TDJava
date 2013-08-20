package Balls;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import Atoms.Atom;
import Atoms.AtomIndex;
import Atoms.AtomNode;
import Helpers.LogHelper;
import Send.SendWrapper;
/**
 * ActiveBall indicates the ball with atom graph. 
 *
 */
public class ActiveBall extends Ball
{
	private AtomicInteger health;
	public int stepLength;
	public BufferedImage healthImage = null;
	public int attack;
	public int scope;
	private AtomNode root;
	private AtomIndex atomIndex;
	private final ActiveBallTarget target;
	
	public ActiveBall(int id, int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(id, x, y, XIZE, YSIZE, imagePath);
		this.stepLength = stepLength;
		this.target = new ActiveBallTarget(null, x, y);
	}
	
	/**
	 * If target is a ball, then target pos is the ball's pos. 
	 * Otherwise, targetball is null. 
	 */
	class ActiveBallTarget{
		volatile int targetX;
		volatile int targetY;
		volatile Ball targetBall;
		
		public ActiveBallTarget(Ball ball, int x, int y){
			if(ball != null){
				this.targetBall = ball;
				this.targetX = ball.getX();
				this.targetY= ball.getY();
			}
			else{
				this.targetBall = null;
				this.targetX = x;
				this.targetY = y;
			}
		}
		
		//Need to acquire lock first : volatile or synchronized. 
		public void updateTarget(Ball ball){
			this.targetBall = ball;
		}
		
		public void updateTarget(int x, int y){
			this.targetX = x;
			this.targetY = y;
		}
	}
	
	public Ball getTargetBall(){
		return this.target.targetBall;
	}
	
	public int getTargetX(){
		return this.target.targetX;
	}
	
	public int getTargetY(){
		return this.target.targetY;
	}
	
	public void setTarget(int x, int y){
			this.target.updateTarget(x, y);
	}
	
	public void setTarget(Ball target){
		this.target.updateTarget(target);
	}
	
	public int walkWay(int n) {
		return n;
	}
	
	public int getHealth(){
		return health.get();
	}
	
	public int setHealth(int x){
		health.set(x);
		SendWrapper.sendBallUpdate(this, "HEALTH", this.health.get());
		return health.get();
	}
	
	@SuppressWarnings("unchecked")
	public void exe(){
		AtomNode temp = this.root;
		for(; temp != null ; ){
			Class atomClass = this.atomIndex.get(temp.id);
			if(atomClass == null){
				LogHelper.warn("AtomIndex return null. Wrong when accessing atom index. " + temp.id);
				break;
			}
			try {
				Object ret = -1;
				if(atomClass.isAssignableFrom(Class.forName("BaseAtom"))){
					Method atomExeMethod = null;
						atomExeMethod = atomClass.getMethod("exe", null);
						ret = atomExeMethod.invoke(null, null);
				}
				else if(atomClass.isAssignableFrom(Class.forName("GroupAtom"))){
					//... 
				}
				else{
					LogHelper.warn("Wrong Atom type");
					break;
				}
				int next = (int)ret;
				if(next >= temp.nibors.length || next < 0){
					LogHelper.warn("Wrong atom exe result. Out of boundary. " + atomClass  + ", " + next );
					break;
				}
				AtomNode nextAtomNode = temp.nibors[next];
				temp = nextAtomNode;
			} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException
					| NoSuchMethodException | SecurityException  | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		LogHelper.warn("Ball atom stop exe. " + this.id);
	}
}