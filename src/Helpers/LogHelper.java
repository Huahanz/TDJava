package Helpers;

public class LogHelper {
	
	public LogHelper()
	{
		
	}
	
	public static void debug(String s) {
		System.out.println("DEBUG : " + s);
	}

	public static void info(String s) {
		System.out.println("INFO : " + s);
	}
	
	public static void warn(String s) {
		System.out.println("WARN : " + s);
	}

	public static void error(String s) {
		System.out.println("ERROR : " + s);
	}
}