package Data;

import java.util.concurrent.atomic.AtomicInteger;

public class LocalMemory {
	Object[] mem;
	Integer size;

	public LocalMemory(int size) {
		if(size <= 0){
			size = 1;
		}
		this.size = size << 1;
		this.mem = new Object[this.size];
	}

	public boolean put(Object obj, int index) {
		if (obj == null || index < 0) {
			return false;
		}
		if (index >= size) {
			this.reconstruct();
		}
		if (index >= size) {
			return false;
		}
		this.mem[index] = obj;
		return true;
	}

	public Object get(){
		
	}
	
	private void reconstruct() {
		synchronized (size) {
			synchronized (mem) {
				Object[] newMem = new Object[size * 2];
				for(int i = 0; i < size; i++){
					newMem[i] = mem[i];
				}
			}
			size <<= 1;
		}
	}

}