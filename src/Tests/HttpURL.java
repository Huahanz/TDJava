package Tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpURL {
	public static void main(String[] args) throws Exception {
		URL oracle = new URL(
				"http://localhost/~huahan/PPServer/index.php/blog/index");
		URLConnection connection = oracle.openConnection();
		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		for (int i = 0; i < 10; i++) {
			connection = oracle.openConnection();
			connection.setDoOutput(true);
//			out.write("my json");
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
		}
		out.close();
		in.close();

	}
}