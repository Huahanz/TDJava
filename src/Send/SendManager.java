package Send;

import Data.QueueManager;

/**
 * Has many references. All methods are static. Need to consider concurrency for
 * all of them. Producer and customer model.
 */
public class SendManager {

	public static void enqueue() {

	}

	public static void dequeue() {

	}

	public static void enqueuePost(int rst) {
		QueueManager.enqueuePVPPost(rst);
	}

}