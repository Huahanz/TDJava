package Tests.Gson;

import com.google.gson.Gson;

import Wrapper.InEntry;

public class TDGsonTest {
	public static void main(String[] args) {
		TDGsonTest test = new TDGsonTest();
		test.testInWrapper();
	}

	public void testInWrapper() {
		String json = "{\"71263287081172992\":[\"assign_balls\",[{\"type_id\":\"0\"}," +
				"{\"type_id\":\"1\"}]],\"65109977630507008\":[\"assign_balls\"," +
				"[{\"type_id\":\"1\"},{\"type_id\":\"1\"}]]}";
		InEntry inEntry = new InEntry(1, 2);
		Gson gson = new Gson();
//		System.out.println(gson.toJson(inEntry));
		int[] ints = {1, 2, 3, 4, 5};
//		String[] str = gson.fromJson(json, String[].class);
		System.out.println();
	}
}