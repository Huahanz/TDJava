package Helpers;

import java.util.ArrayList;
import java.util.Stack;

public class SocketHelper{
	static Stack<Object> sendQueue = new Stack<Object>();
	public static synchronized void addToQueue(Object obj){
		sendQueue.add(obj);
	}
	public static boolean isQueueEmpty(){
		return sendQueue.size() == 0;
	}
	public synchronized static Object popFromQueue() {
		return sendQueue.pop();
	}
}