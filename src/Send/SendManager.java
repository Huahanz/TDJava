package Send;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import worker.Scheduler;

import Controller.HttpManager;
import Data.QueueManager;
import balls.Ball;

/**
 * Has many references. All methods are static. Need to consider concurrency for all of them. 
 * Producer and customer model.
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