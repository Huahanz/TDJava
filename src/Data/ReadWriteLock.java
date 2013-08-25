package Data;

import java.util.HashMap;

/**
 * This level 2 rw lock .
 *  level 0 -> basic rw lock. 
 *  level 1 -> write perference. 
 *  level 2 -> fairness. 
 *   
 * Test cases : T0 getReadLock, T0 getWriteLock.
 * 0getw, 0getr
 * 1getr, 0getw
 * 1getw, 0getr
 * 0getw, 0getw
 * 0getr, 0getr
 * 
 * TODO unit test. 
 */
public class ReadWriteLock {
	HashMap<Thread, Integer> readCounts = new HashMap<Thread, Integer>();
	int writeAccess = 0;
	Thread writing = null;
	private static double readThreshold = 0.5;

	public void getReadLock() {
		while (!readable()) {
			try {
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!this.readCounts.containsKey(Thread.currentThread())) {
			this.readCounts.put(Thread.currentThread(), 0);
		}
		this.readCounts.put(Thread.currentThread(),
				this.readCounts.get(Thread.currentThread()) + 1);
	}

	public void releaseReadLock() {
		int val = this.readCounts.get(Thread.currentThread()) - 1;
		if (val <= 0) {
			this.readCounts.remove(Thread.currentThread());
		} else {
			this.readCounts.put(Thread.currentThread(), val);
		}
		notifyAll();
	}

	public void getWriteLock() {
		this.writeAccess++;
		while (!this.writable()) {
			try {
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.writeAccess--;
		this.writing = Thread.currentThread();
	}

	public void releaseWriteLock() {
		this.writing = null;
		notifyAll();
	}

	private boolean readable(){
		if((this.writing == Thread.currentThread())){
			return true;
		}
		double rand = Math.random();
		return (rand > ReadWriteLock.readThreshold || this.writeAccess == 0 ) && this.writing == null;
	}
	
	private boolean writable() {
		boolean writable = !this.readCounts.containsKey(Thread.currentThread());
		if (writable) {
			return true;
		}
		writable = this.readCounts.get(Thread.currentThread()) > 0
				|| this.writing == null || this.writing == Thread.currentThread();
		return writable;
	}
}
