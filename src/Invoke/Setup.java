package Invoke;

import javax.swing.JFrame;

import swingFrontEnd.GameInfo;
import swingFrontEnd.SwingFrame;
import worker.Executor;
import Request.Requester;
import Simulator.Simulator;

/**
 * 
 * We need to ensure that the underlaying classes are thread safe. 
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
		sim.setupPVPForwardQueue();
	}
	
	public static void startServer(){
		setEnv();
		startRequest();
		startSender();
		startExe();		
	}
	
	public static void startSender() {
		
	}

	public static void setEnv()
	{
		//calculate map short path
		GameInfo.loadMap();
	}
	
	public static void startRequest()
	{
		Thread requesterThread = new Thread(new Requester());
		requesterThread.start();
	}
	
	public static void startExe()
	{
		Executor exe = new Executor();
		Object rst = exe.start();
	}
	
}