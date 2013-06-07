package Invoke;

import javax.swing.JFrame;

import swingFrontEnd.GameInfo;
import swingFrontEnd.SwingFrame;
import worker.Executor;
import Request.Requester;
import Simulator.Simulator;

public class Main
{
	public static void main(String[] args){
		Setup setup = new Setup();
		setup.startSimulator();
		setup.startServer();
	}
	}