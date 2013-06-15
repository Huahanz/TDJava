package Wrapper;

import Send.SendWrapper;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Static class help to encapsulate json operations. 
 * All method are atomic. 
 *
 */
public class Parser
{
	public Parser()
	{
		
	}
	
	//If the return value from PHPServer is Object, it got parse to a linkedtreemap. Otherwise, it parsed to a string.  
	public static Object parseIn(String json)
	{
		Gson gson = new Gson();
		LinkedTreeMap rst = (LinkedTreeMap) gson.fromJson(json, Object.class);
		String className = (String) rst.get("class");
		String method = (String) rst.get("method");
		Object rtnVal = rst.get("val");
		return rtnVal;
	}
	
	public static LinkedTreeMap getRtnVal(String json){
		Gson gson = new Gson();
		LinkedTreeMap rst = (LinkedTreeMap) gson.fromJson(json, Object.class);
		String className = (String) rst.get("class");
		String method = (String) rst.get("method");
		LinkedTreeMap rtnVal = (LinkedTreeMap) rst.get("val");
		return rtnVal;
	}
	
	public static LinkedTreeMap getRtnVal(LinkedTreeMap serverWrapper){
		LinkedTreeMap rtnVal = (LinkedTreeMap) serverWrapper.get("val");
		return rtnVal;
	}
	
	public static SendWrapper[] parseOut()
	{
		return null;
	}
	
}