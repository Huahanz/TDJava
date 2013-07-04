package Tests.Performance;

import Send.Postman;

public class PostTester implements ServerResponseTester{

	public void testServiceResponse() {
		Postman postman = new Postman();
		postman.post();
	}
	
}