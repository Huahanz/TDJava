package Data;

import Wrapper.InWrapper;
import Wrapper.OutWrapper;

/*
 * Manager two group of queues : income queues and outgoing queues.
 *  
 */
public class QueueManager {
	private static QueueManager instance = null;

	private QueueManager() {

	}

	public static QueueManager getInstance() {
		if (instance == null) {
			instance = new QueueManager();
		}
		return instance;
	}

	/*
	 * @var: pvp_id
	 * 
	 * @return boolean
	 */
	public boolean pushOutQueue(int pvpID, OutWrapper outWrapper) {
		return true;
	}

	public static boolean pushInQueue(int pvpID, InWrapper inWrapper) {
		return true;
	}

	public InWrapper popInQueue(int pvpID) {
		return null;
	}

	public OutWrapper popOutQueue(int pvpID) {
		return null;
	}

	public InWrapper peekInQueue(int pvpID) {
		return null;
	}

	public OutWrapper peekOutQueue(int pvpID) {
		return null;
	}

	public int sizeOfInQueue(int pvpID) {
		return 0;
	}

	public int sizeOfOutQueue(int pvpID) {
		return 0;
	}
}