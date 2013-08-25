package Balls;

import Atoms.Atom;
import Atoms.AtomIndex;
import Atoms.AtomNode;
import Atoms.BaseAtom;
import Atoms.GroupAtom;
import Helpers.LogHelper;

public class AtomBall extends Ball{
	private final ActiveBallTarget target;
	private AtomNode root;
	private AtomIndex atomIndex;
	public int scope;

	public AtomBall(int id, int x, int y, int XSIZE, int YSIZE, String imagePath, int scope) {
		super(id, x, y, XSIZE, YSIZE, imagePath);
		this.target = new ActiveBallTarget(null, x, y);
		this.scope = scope;
	}
	
	/**
	 * If target is a ball, then target pos is the ball's pos. Otherwise,
	 * targetball is null.
	 */
	class ActiveBallTarget {
		volatile int targetX;
		volatile int targetY;
		volatile Ball targetBall;

		public ActiveBallTarget(Ball ball, int x, int y) {
			if (ball != null) {
				this.targetBall = ball;
				this.targetX = ball.getX();
				this.targetY = ball.getY();
			} else {
				this.targetBall = null;
				this.targetX = x;
				this.targetY = y;
			}
		}

		// Need to acquire lock first : volatile or synchronized.
		public void updateTarget(Ball ball) {
			this.targetBall = ball;
		}

		public void updateTarget(int x, int y) {
			this.targetX = x;
			this.targetY = y;
		}
	}

	public Ball getTargetBall() {
		return this.target.targetBall;
	}

	public int getTargetX() {
		return this.target.targetX;
	}

	public int getTargetY() {
		return this.target.targetY;
	}

	public void setTarget(int x, int y) {
		this.target.updateTarget(x, y);
	}

	public void setTarget(Ball target) {
		this.target.updateTarget(target);
	}

	public int walkWay(int n) {
		return n;
	}
	
	public void exe() {
		AtomNode temp = this.root;
		for (; temp != null;) {
			Atom atom = this.atomIndex.get(temp.id);
			if (atom == null) {
				LogHelper
						.warn("AtomIndex return null. Wrong when accessing atom index. "
								+ temp.id);
				break;
			}
			Object ret = -1;
			if (atom instanceof BaseAtom) {
				atom.exe(this);
			} else if (atom instanceof GroupAtom) {
				// ...
				atom.exe(this);
			} else {
				LogHelper.warn("Wrong Atom type");
				break;
			}
			int next = (int) ret;
			if (next >= temp.nibors.length || next < 0) {
				LogHelper.warn("Wrong atom exe result. Out of boundary. "
						+ atom.getClass().toString() + ", " + next);
				break;
			}
			AtomNode nextAtomNode = temp.nibors[next];
			temp = nextAtomNode;
		}
		LogHelper.warn("Ball atom stop exe. " + this.id);
	}
}