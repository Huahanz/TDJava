package Tests.Gson;

import com.google.gson.Gson;

//If a field is marked transient, (by default) it is ignored and not included in the JSON serialization or deserialization.


public class GsonTest {
	public static void main(String[] args) {
		BagOfPrimitives obj = new BagOfPrimitives();
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		System.out.println(json);
		BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
		System.out.println(obj2);
	}	
}

class BagOfPrimitives extends Object {
	private int value1 = 1;
	private String value2 = "abc";
	private transient int value3;

	BagOfPrimitives() {
		// no-args constructor
		System.out.println("int bp contructor");
		this.value3 = 30;
	}
}
