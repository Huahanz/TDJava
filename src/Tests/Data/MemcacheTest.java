package Tests.Data;

import Data.MemcacheManager;

public class MemcacheTest{
	public static void main(String[] args){
		MemcacheTest mem = new MemcacheTest();
		mem.basicTest();
	}
	
	public void basicTest(){
		int slot = 0;
		MemcacheManager mem = new MemcacheManager(slot);
		String key = "mykey";
		mem.set(key, "123", 0);
		System.out.println(mem.get(key));
	}
}