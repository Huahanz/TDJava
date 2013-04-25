package Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import FrontEnd.GameInfo;

public class SocketRunnable implements Runnable {
	public SocketRunnable() {

	}

	public void run() {

		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(Config.serverAddr, Config.serverPort);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + Config.serverAddr);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to: "
					+ Config.serverAddr);
		}

		while (true) {
			while (!SocketHelper.isQueueEmpty()) {
				Object x = SocketHelper.popFromQueue();
				out.println(x);

			}
		}

	}

}