package Invoke;

import java.io.IOException;

import javax.swing.JFrame;

import swingFrontEnd.GameInfo;
import swingFrontEnd.SwingFrame;
import worker.Executor;
import Request.Requester;
import Simulator.Simulator;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Setup setup = new Setup();
		setup.startSimulator();
		setup.startServer();
	}
}