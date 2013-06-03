package Invoke;

import swingFrontEnd.GameInfo;
import worker.Executor;
import Request.Requester;
import Tests.Simulate.Simulator;

public class Main
{
	public static void main(String[] args){
		Main main = new Main();
		main.startSimulator();
//		main.startServer();
	}
	
	private void startSimulator()
	{
		Simulator sim = new Simulator();
		sim.setupPVPForwardQueue();
	}
	
	private void startServer(){
		this.setEnv();
		this.startRequest();
		this.startSender();
		this.startExe();		
	}
	
	private void startSender() {
		
	}

	private void setEnv()
	{
		//calculate map short path
		GameInfo.loadMap();
	}
	
	private void startRequest()
	{
		Thread requesterThread = new Thread(new Requester());
		requesterThread.start();
	}
	
	private void startExe()
	{
		Executor exe = new Executor();
		Object rst = exe.start();
	}
}