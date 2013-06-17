package Tests.Integration;

import java.io.IOException;

import Invoke.Setup;

public class IntegrationTest{
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		Setup setup = new Setup();
		setup.startSimulator();
		setup.startServer();
	}
}
