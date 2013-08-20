package deprecatedBalls;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import Helpers.Config;
import Helpers.LogHelper;
import Send.SendWrapper;
import swingFrontEnd.GameInfo;
import worker.Executor;

public abstract class BallRunnable implements Runnable{

	public static CyclicBarrier barrier = new CyclicBarrier(4);
	public BallRunnable(){
	}
	public void run() {
		int count = SendWrapper.checkSeqNum();
		while (count < Config.numPerRound) {
			job();
			try {
				Thread.sleep(Config.ballThreadWait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count = SendWrapper.checkSeqNum();
		}
		LogHelper.debug("arrive the limit, notifing others. ");
		
		this.stop();
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
	}
	protected boolean stopped = false;
	private void stop(){
		stopped = true;
	}
	public boolean isStopped(){
		return stopped;
	}
	protected abstract void job();
}