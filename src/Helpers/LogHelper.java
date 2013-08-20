package Helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
	
	private static final Logger LOGGER = Logger.getLogger(
		    Thread.currentThread().getStackTrace()[2].getClassName() );
	
	public LogHelper()
	{
		
	}
	
	public static void debug(String s) {
		System.out.println("DEBUG : " + s);
		System.out.println(LogHelper.getContext());
	}

	public static void info(String s) {
//		System.out.println("INFO : " + s);
	}
	
	public static void warn(String s) {
		System.out.println("WARN : " + s);
		System.out.println(LogHelper.getContext());
	}

	public static void error(String s) {
		System.out.println("ERROR : " + s);
		System.out.println(LogHelper.getContext());
	}
	

	private static String getContext(){
		return Thread.currentThread().getStackTrace().toString();
	}
}