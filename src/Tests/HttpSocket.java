package Tests;

import java.io.*;
import java.net.*;

public class HttpSocket {
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;

	public static void main(String[] args){
		HttpSocket client = new HttpSocket();
		client.test();
	}
	public void test() {
		try {
			socket = new Socket("127.0.0.1", 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			for (int i = 0; i < 10; i++) {
				Object returnJson = null;
				try {
					returnJson = in.readObject();
					out.writeObject("my message " + i);
					out.flush();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println(returnJson);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}