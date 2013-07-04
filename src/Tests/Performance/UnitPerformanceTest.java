package Tests.Performance;

import java.io.IOException;

import Helpers.TestHelper;
import Invoke.Setup;
import Simulator.Simulator;

public class UnitPerformanceTest {
	// The time should include the json decode and queue operation.
	// The PHP services called by Java server : pop_recent_updates,
	// TODO using multiple requesters and multiple senders.

//	private long singleTest(){
//		
//	}
	
	private long testSingleServerResponse(ServerResponseTester obj) {
		long start = System.nanoTime();
		obj.testServiceResponse();
		long end = System.nanoTime();
		return start - end;
	}
	
	public void testServerResponse(){
		int singleRounds = 100;
		long stats = 0;
		for(int i = 0; i < singleRounds; i++){
			long delta = this.testSingleServerResponse(new RequestTester());
			stats += delta; 
		}
		long unit = stats / singleRounds;
		TestHelper.print("RequestTester " + unit);
		
		stats = 0;
		for(int i = 0; i < singleRounds; i++){
			long delta = this.testSingleServerResponse(new PostTester());
			stats += delta; 
		}
		unit = stats / singleRounds;
		TestHelper.print("PostTester " + unit);
	}
	
	private void setup(int playerSize, int ballSize, int pvpSize) throws ClassNotFoundException, IOException{
		// #request = 
		Simulator sim = new Simulator();
		sim.clearData();
		
		// setup post queue
		sim.setupPVPForwardQueue(playerSize, ballSize, pvpSize);
		Setup.setEnv();
		Setup.startRequest();
		Setup.startExe(100);
		
		// setup request queue
		sim.setupPVPForwardQueue(playerSize, ballSize, pvpSize);

		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		UnitPerformanceTest pt = new UnitPerformanceTest();
		pt.setup(0, 0, 0);
		pt.testServerResponse();
	}
}