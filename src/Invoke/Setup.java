package Invoke;

import javax.swing.JFrame;

import swingFrontEnd.GameInfo;
import swingFrontEnd.SwingFrame;
import worker.Executor;
import Helpers.LogHelper;
import Request.Requester;
import Send.Postman;
import Simulator.Simulator;

/**
 * 
 * We need to ensure that the underlying classes are thread safe. And above of that, we need to make sure all the references don't have interleave.
 * The thread safety of this class is depend on Simulator.java, SwingFrame.java, GameInfo.java and Executor.java
 *
 */
public class Setup
{
	public static void startPanel(){
		JFrame frame = new SwingFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void startSimulator()
	{
		Simulator sim = new Simulator();
		sim.clearData();
		sim.setupPVPForwardQueue();
	}
	
	public static void startServer(){
		setEnv();
		startRequest();
		startSender();
		startPanel();
		startExe(0);		
	}
	
	public static void startSender() {
		Thread sendThread = new Thread(new Postman());
		sendThread.start();
	}

	public static void setEnv()
	{
		//calculate map short path
		GameInfo.loadMap();
		LogHelper.debug("finish seting up env");
	}
	
	public static void startRequest()
	{
		Thread requesterThread = new Thread(new Requester());
		requesterThread.start();
	}
	
	public static void startExe(int rounds)
	{
		Executor exe = new Executor();
		for(int i = 0; i <= rounds; i++){
			LogHelper.debug("start the next round ");
			Object rst = exe.start();
			if(rounds == 0){
				i--;
			}
		}
	}
	
}