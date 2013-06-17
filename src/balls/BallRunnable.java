package balls;

import Helpers.Config;
import Helpers.LogHelper;
import Send.SendWrapper;
import swingFrontEnd.GameInfo;
import worker.Executor;

public abstract class BallRunnable implements Runnable{

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
//			notifyAll();
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