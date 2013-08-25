package Atoms;

import swingFrontEnd.GameInfo;
import Balls.ActiveBall;
import Balls.AtomBall;
import Balls.Ball;
import Balls.BulletBall;
import Balls.DragonBall;
import Balls.TowerBall;
import Helpers.BallCache;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;
import Send.SendWrapper;

/**
 * TODO null check.
 * 
 */
public abstract class Atom {
	public abstract Object exe(AtomBall ball);
}

class MoveWithBreakAtom extends BaseAtom {
	public Object exe(AtomBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		IsBlockedAtom isBlockedAtom = new IsBlockedAtom();
		if ((boolean) isBlockedAtom.exe(ball)) {
			MoveToBreakBlockAtom moveToBreakBlockAtom = new MoveToBreakBlockAtom();
			return moveToBreakBlockAtom.exe(ball);
		} else {
			MoveAtom moveAtom = new MoveAtom();
			return moveAtom.exe(ball);
		}
	}
}

/**
 * Call MoveInSlotAtom. Move level 8. Move with precalculated Dijkstra table.
 */
class MoveAtom extends BaseAtom {
	public Object exe(AtomBall ball){
		return this.exe((ActiveBall)ball);
	}
	public Object exe(ActiveBall ball) {
		int x = ball.getTargetX();
		int y = ball.getTargetY();
		int ballX = ball.getX();
		int ballY = ball.getY();
		if (!GameInfo.isValide(x, y) || !GameInfo.isValide(ballX, ballY)){
			return 0;
		}
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
 * Bottom atom. MoveInSlotAtom level 2. Move to target directly. Ratio is decide
 * by abs(dx) + abs(dy).
 */
class MoveInSlotAtom extends BaseAtom {
	public Object exe(AtomBall ball){
		return this.exe((ActiveBall)ball);
	}
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
	public Object exe(AtomBall ball) {
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
	public Object exe(AtomBall ball){
		return this.exe((ActiveBall)ball);
	}
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
			ball.putLocalCache("xSlot", thisXSlot, 0);
			ball.putLocalCache("ySlot", thisYSlot, 0);
			BreakBlockAtom breakBlockAtom = new BreakBlockAtom();
			breakBlockAtom.exe(ball);
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
	public Object exe(AtomBall ball) {
		int xSlot = (int) ball.getLocalCache("xSlot");
		int ySlot = (int) ball.getLocalCache("ySlot");
		TestHelper.print("Breaking");
		GameInfo.currentMap[ySlot][xSlot] = 0;
		SendWrapper.sendBallAction(ball, "BREAKBLOCK");
		return true;
	}
}

class AttackAtom extends BaseAtom {
	public Object exe(AtomBall ball){
		return this.exe((ActiveBall)ball);
	}
	public Object exe(ActiveBall ball) {
		AtomBall target = (AtomBall) ball.getTargetBall();
		if (target == null) {

		} else if (!(target instanceof ActiveBall)) {

		} else {
			SendWrapper.sendBallAction(ball, "ATTACK");
			((ActiveBall) target).addAndGetHealth(-ball.attack);
			if (((ActiveBall) target).getHealth() <= 0) {
				GameManager gm = GameManager.getInstance();
				gm.killBall(target, false);
				return true;
			}
		}
		return false;
	}
}

class IsInScopeAtom extends BaseAtom {
	public Object exe(AtomBall ball) {
		int x = ball.getX();
		int y = ball.getY();
		int scope = ball.scope;
		int ballX = ball.getX();
		int ballY = ball.getY();
		return (Math.pow(ballX - x, 2) + Math.pow(ballY - y, 2) <= Math.pow(
				scope, 2));
	}
}

class SilverBulletShoot extends BaseAtom {
	public Object exe(AtomBall ball) {
		return this.exe((BulletBall)ball);
	}
	public Object exe(BulletBall ball) {
		if (!(ball instanceof BulletBall)) {
			return false;
		}
		MoveInSlotAtom moveInSlotAtom = new MoveInSlotAtom();
		boolean hit = (boolean) moveInSlotAtom.exe(ball);
		if (hit) {
			Ball target = ball.getTargetBall();
			if (target instanceof DragonBall) {
				int health = ((DragonBall) target)
						.addAndGetHealth(ball.attack);
				if (health <= 0) {
					target.imagePath = Config.DieImagePath;
					GameManager gm = GameManager.getInstance();
					gm.killBall(target, false);
					gm.killBullet((BulletBall) ball);
					return 2;
				}
			}
			return 1;
		}
		return 0;
	}
}

class MoveToExitAtom extends BaseAtom {
	public Object exe(AtomBall ball) {
		int thisXSlot = ball.getX() / Config.slotWidth;
		int thisYSlot = ball.getY() / Config.slotHeight;
		int toXSlot = (Config.defaultOneSlotWidth - Config.slotWidth)
				/ Config.slotWidth;
		int toYSlot = (Config.defaultOneSlotHeight - Config.slotHeight)
				/ Config.slotHeight;
		if (thisXSlot == toXSlot && thisYSlot == toYSlot) {
			GameManager gm = GameManager.getInstance();
			gm.reachDestination(ball);
			return true;
		} else {
			ball.setTarget(Config.mapWidth - Config.slotWidth, Config.mapHeight
					- Config.slotHeight);
			MoveWithBreakAtom moveWithBreakAtom = new MoveWithBreakAtom();
			moveWithBreakAtom.exe(ball);
			return false;
		}
	}
}

class TowerAttckAtom extends BaseAtom {
	public Object exe(AtomBall ball) {
		if (!(ball instanceof TowerBall)) {
			return 0;
		}
		GameManager gameManager = GameManager.getInstance();
		int id = BallCache.generateBallID();
		String bulletName = ((TowerBall) ball).bulletName;
		gameManager.addBall(bulletName, ball.getX(), ball.getY(), ball, id);
		return 1;
	}

}

class TowerDefendAtom extends BaseAtom {
	public Object exe(AtomBall ball) {
		for (int i = 0; i < GameInfo.balls.size(); i++) {
			//TODO  better global index. 
			Ball target = GameInfo.balls.get(i);
			if (target instanceof DragonBall) {
				IsInScopeAtom isInScopeAtom = new IsInScopeAtom();
				ball.setTarget(target);
				if ((boolean) isInScopeAtom.exe(ball)) {
					TowerAttckAtom towerAttckAtom = new TowerAttckAtom();
					return towerAttckAtom.exe(ball);
				}
			}
		}
		return false;
	}
}

class DTowerDefendAtom extends BaseAtom {
	public Object exe(AtomBall ball){
		if(! (ball instanceof TowerBall)){
			return 0;
		}
		Ball target = ball.getTargetBall();
		int angle = CalculateAngleAtom.calculateAngle(ball.getX(), ball.getY(), target.getX(),
				target.getY());
		GameInfo.currentMap[ball.getY() / Config.slotHeight][ball.getX()
				/ Config.slotWidth] = ((TowerBall) ball).mapID + angle;
		GameManager gameManager = GameManager.getInstance();
		int id = BallCache.generateBallID();
		gameManager.addBall(((TowerBall) ball).bulletName, ball.getX(), ball.getY(),
				target, id);
		SendWrapper.sendBallAction(ball, "TOWERATTACK");
		return true;
	}
}

//TODO 
class RandomWalkAtom extends BaseAtom {
	public Object exe(AtomBall ball) {
		return 0;
	}
	
}
