package Atoms;

import swingFrontEnd.GameInfo;
import Balls.ActiveBall;
import Balls.Ball;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;
import Send.SendWrapper;

public abstract class Atom {
	public abstract Object exe(ActiveBall ball);
	public final Object exe(ActiveBall ball, int x, int y){
		//Should never be called. 
		
	}
}

class GroupAtom extends Atom {
	int id; 
}

class BaseAtom extends Atom {
	
}

class MoveAndBreakAtom extends BaseAtom {
	public Object exe(ActiveBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		if ((boolean) IsBlockedAtom.exe(ball, x, y)) {
			return MoveToBreakBlockAtom.exe(ball, x, y);
		} else {
			return MoveAtom.exe(ball);
		}
	}
}

/**
 * Call MoveInSlotAtom. Move level 8. 
 * Move with precalculated Dijkstra table. 
 */
class MoveAtom extends BaseAtom {
	public Object exe(ActiveBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		int ballX = ball.getX();
		int ballY = ball.getY();
		if (!GameInfo.isValide(x, y) || !GameInfo.isValide(ballX, ballY))
			return false;
		int thisXSlot = ballX / Config.slotWidth;
		int thisYSlot = ballY / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		boolean isArrive = false;
		if (thisXSlot == toXSlot && thisYSlot == toYSlot) {
			ball.setTarget(x, y);
			MoveInSlotAtom moveInSlotAtom = new MoveInSlotAtom();
			isArrive = (boolean) moveInSlotAtom.exe(ball);
		} else {
			byte dir = GameInfo.mapDir[thisYSlot][thisXSlot][toYSlot][toXSlot];
			int step = ball.stepLength;
			if ((dir & 1) == 0) {
				step >>= 1;
			}
			if (dir == 1 || dir == 2 || dir == 8) {
				ballY = ball.addAndGetY(-step);
			}
			if (dir >= 2 && dir <= 4) {
				ballX = ball.addAndGetX(step);
			}
			if (dir >= 4 && dir <= 6) {
				ballY = ball.addAndGetY(step);
			}
			if (dir >= 6 && dir <= 8) {
				ballX = ball.addAndGetX(-step);
			}
		}
		SendWrapper.sendBallMove(ball, ballX, ballY, 0);
		return isArrive;
	}
}

/**
 * Bottom atom. MoveInSlotAtom level 2. 
 * Move to target directly. Ratio is decide by abs(dx) + abs(dy).
 */
class MoveInSlotAtom extends BaseAtom {
	public Object exe(ActiveBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		int boundMinX = 0;
		int boundMinY = 0;
		int boundMaxX = Config.defaultOneSlotWidth;
		int boundMaxY = Config.defaultOneSlotHeight;
		int ballX = ball.getX();
		int ballY = ball.getY();

		int dx = Math.abs(x - ballX);
		int dy = Math.abs(y - ballY);
		int dis = dx + dy;
		if (ball.stepLength > dis) {
			ball.setX(x);
			ball.setY(y);
			return true;
		}
		double steplen = ball.stepLength;
		double ratio = steplen / dis;
		if (dx != 0) {
			dy = (int) (dy * ratio);
			if (ballY > y) {
				dy = -dy;
			}
		}
		if (dy != 0) {
			dx = (int) (dx * ratio);
			if (ballX > x) {
				dx = -dx;
			}
		}
		ballX = ball.addAndGetX(ball.walkWay(dx));
		ballY = ball.addAndGetY(ball.walkWay(dy));

		if (ballX < boundMinX) {
			ball.setX((int) boundMinX);
		}
		if (ball.getX() + ball.XSIZE >= boundMaxX) {
			ball.setX((int) boundMaxX - ball.XSIZE);
		}
		if (ball.getY() < boundMinY) {
			ball.setY((int) boundMinY);

		}
		if (ball.getY() + ball.YSIZE >= boundMaxY) {
			ball.setY((int) boundMaxY - ball.YSIZE);
		}

		return false;
	}
}

/**
 * 
 * 
 */
class IsBlockedAtom extends BaseAtom {
	public Object exe(ActiveBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		int ballX = ball.getX();
		int ballY = ball.getY();
		int thisXSlot = ballX / Config.slotWidth;
		int thisYSlot = ballY / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		if (!GameInfo.isValide(x, y) || !GameInfo.isValide(ballX, ballY)) {
			return false;
		}
		if (thisXSlot == toXSlot && thisYSlot == toYSlot) {
			return false;
		}
		byte dir = GameInfo.mapDir[thisYSlot][thisXSlot][toYSlot][toXSlot];
		return dir == 0;
	}
}

class MoveToBreakBlockAtom extends BaseAtom {
	public Object exe(ActiveBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		int ballX = ball.getX();
		int ballY = ball.getY();
		if (!GameInfo.isValide(x, y) || !GameInfo.isValide(ballX, ballY)) {
			return false;
		}
		int thisXSlot = ballX / Config.slotWidth;
		int thisYSlot = ballY / Config.slotHeight;
		int toXSlot = x / Config.slotWidth;
		int toYSlot = y / Config.slotHeight;
		// if (thisXSlot == toXSlot && thisYSlot == toYSlot)
		// return breakBlock(thisXSlot, thisYSlot);

		if (GameInfo.currentMap[thisYSlot][thisXSlot] != 0) {
			BreakBlockAtom.exe(ball, thisXSlot, thisYSlot);
		}
		byte dir = GameInfo.breakDir[thisYSlot][thisXSlot][toYSlot][toXSlot];

		int step = ball.stepLength;
		if ((dir & 1) == 0) {
			step >>= 1;
		}
		if (dir == 1 || dir == 2 || dir == 8) {
			ballY = ball.addAndGetY(-step);
		}
		if (dir >= 2 && dir <= 4) {
			ballX = ball.addAndGetX(step);
		}
		if (dir >= 4 && dir <= 6) {
			ballY = ball.addAndGetY(step);
		}
		if (dir >= 6 && dir <= 8) {
			ballX = ball.addAndGetX(-step);
		}
		SendWrapper.sendBallMove(ball, ballX, ballY, 0);
		return false;
	}
}

class BreakBlockAtom extends BaseAtom {
	public static Object exe(ActiveBall ball, int xSlot, int ySlot) {
		TestHelper.print("Breaking");
		GameInfo.currentMap[ySlot][xSlot] = 0;
		SendWrapper.sendBallAction(ball, "BREAKBLOCK");
		return true;
	}
}

class AttackAtom extends BaseAtom {
	public static Object exe(ActiveBall ball, ActiveBall target) {
		SendWrapper.sendBallAction(ball, "ATTACK");
		target.health.addAndGet(-ball.attack);
		if (((ActiveBall) ball).getHealth() <= 0) {
			GameManager gm = GameManager.getInstance();
			gm.killBall(target, false);
		}
		return false;
	}
}

class IsInScopeAtom extends BaseAtom {
	public static Object exe(ActiveBall ball, int x, int y) {
		int scope = ball.scope;
		int ballX = ball.getX();
		int ballY = ball.getY();
		return (Math.pow(ballX - x, 2) + Math.pow(ballY - y, 2) <= Math.pow(
				scope, 2));
	}
}
