package Tests.Integration;

import Invoke.Setup;

public class IntegrationTest{
	public static void main(String[] args){
		Setup setup = new Setup();
		setup.startSimulator();
		setup.startServer();
	}
}
