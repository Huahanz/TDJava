package Balls;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import Atoms.Atom;
import Atoms.AtomIndex;
import Atoms.AtomNode;
import Atoms.BaseAtom;
import Atoms.GroupAtom;
import Data.LocalCache;
import Data.LocalCacheNode;
import Data.UnstackLocalCacheNode;
import Helpers.LogHelper;
import Send.SendWrapper;

/**
 * Some of the atomball have health, like dragon. Some of them don't, like tower.
 * For now, activeBall just means the ball with health.  
 */
public class ActiveBall extends AtomBall {
	private AtomicInteger health;
	public BufferedImage healthImage = null;
	public int stepLength;
	public int attack;
	
	public ActiveBall(int id, int x, int y, int XIZE, int YSIZE,
			int stepLength, String imagePath, int attack, int scope, int health) {
		super(id, x, y, XIZE, YSIZE, imagePath, scope);
		this.health = new AtomicInteger(health);
		this.stepLength = stepLength;
		this.attack = attack;
	}

	public int getHealth() {
		return health.get();
	}

	public int setHealth(int x) {
		health.set(x);
		SendWrapper.sendBallUpdate(this, "HEALTH", this.health.get());
		return health.get();
	}

	public int addAndGetHealth(int delta) {
		return this.health.addAndGet(delta);
	}
}