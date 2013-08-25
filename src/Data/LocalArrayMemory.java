package Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Deprecated before reorginze. 
 * implement the executor and future version.
 * implement read and write lock for every slot.  
 */
public class LocalArrayMemory {
	Object[] mem;
	Integer size;
	ReadWriteLock reconstructLock;
	ReadWriteLock[] slotLock;

	public LocalArrayMemory(int size) {
		if (size <= 0) {
			size = 1;
		}
		this.size = size << 1;
		this.mem = new Object[this.size];
		this.slotLock = new ReadWriteLock[this.size];
		for(int i = 0; i < this.size; i++){
			slotLock[i] = new ReadWriteLock();
		}
		this.reconstructLock = new ReadWriteLock();
	}

	public boolean put(Object obj, Integer index) {
		if (obj == null || index < 0) {
			return false;
		}
		if (index >= size) {
			this.resize();
		}
		if (index >= size) {
			return false;
		}
		this.slotLock[index].getWriteLock();
		this.mem[index] = obj;
		this.slotLock[index].releaseWriteLock();
		notifyAll();
		return true;
	}

	public Object get(Integer index) {
		if (index < 0 || index >= size) {
			return null;
		}
		this.slotLock[index].getReadLock();
		Object obj = mem[index];
		this.slotLock[index].releaseReadLock();
		return obj;
	}

	private synchronized void resize() {
		synchronized(this.slotLock){
			for (int i = 0; i < this.size; i++) {
				this.slotLock[i].getWriteLock();
			}
			Object[] newMem = new Object[size * 2];
			for (int i = 0; i < size; i++) {
				newMem[i] = mem[i];
			}
			for (int i = 0; i < this.size; i++) {
				this.slotLock[i].releaseWriteLock();
			}
			size <<= 1;
			this.slotLock = new ReadWriteLock[size];
			for(int i = 0; i < this.size; i++){
				this.slotLock[i] = new ReadWriteLock();
			}
		}
	}

}