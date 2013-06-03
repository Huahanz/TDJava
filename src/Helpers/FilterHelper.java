package Helpers;

import java.util.ArrayList;

/**
 * Static class. All functions are atomic. 
 *
 */
public class FilterHelper
{
	public FilterHelper()
	{
		
	}
	public static ArrayList randFilterArrayList(ArrayList arr, double weight){
		ArrayList rst = new ArrayList();
		for(Object obj : arr){
			double rand = Math.random();
			if(rand <= weight){
				rst.add(obj);
			}
		}
		return rst;
	}
}