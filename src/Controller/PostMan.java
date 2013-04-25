package Controller;

import Helpers.TestHelper;

public class PostMan {
	public PostMan() {

	}

	public void send(String message) {
		SocketHelper.addToQueue(message);
	}

}