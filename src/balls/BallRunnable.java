package balls;

import Helpers.Config;
import Helpers.LogHelper;
import swingFrontEnd.GameInfo;
import worker.Executor;

public abstract class BallRunnable implements Runnable{

	public BallRunnable(){
	}
	public void run() {
		int count = Executor.count.get();
		while (count < Config.numPerRound) {
			job();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count = Executor.count.getAndIncrement();
		}
		LogHelper.debug("arrive the limit, notifing others. ");
		notifyAll();
	}
	protected abstract void job();
}