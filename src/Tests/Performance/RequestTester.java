package Tests.Performance;

import Request.RequestManager;

public class RequestTester implements ServerResponseTester {
	public void testServiceResponse(){
		RequestManager r = new RequestManager();
		r.makeRequest(0);
	}
}