package Threads;

import java.util.concurrent.Callable;

import Helpers.LogHelper;

public class BallThread implements Callable
{
	int duration;
	String msg;
	public BallThread(int duration, String msg){
		this.duration = duration;
		this.msg = msg;
	}
	
	public Object call() throws Exception {
		return null;
	}
		
}