package Tests.Threads;

import java.util.HashSet;
import java.util.concurrent.Callable;

import Threads.BallExecutor;
import Threads.BallThread;

public class BallThreadTest
{
	public static void main(String[] args){
		BallThread ballThread0 = new BallThread(100, "111");
		BallThread ballThread1 = new BallThread(200, "222");
		HashSet<Callable> ballThreads = new HashSet<Callable>();
		ballThreads.add(ballThread0);
		ballThreads.add(ballThread1);
		BallExecutor ballExecutor = new BallExecutor(ballThreads);
		ballExecutor.start();
	}
}