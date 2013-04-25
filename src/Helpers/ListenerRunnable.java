package Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import FrontEnd.GameInfo;

public class ListenerRunnable implements Runnable {
	public ListenerRunnable() {

	}

	public void run() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(Config.listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			Socket incoming;
			try {
				incoming = s.accept();

				InputStream inStream = incoming.getInputStream();

				Scanner in = new Scanner(inStream);
				

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}