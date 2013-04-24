package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.brandao.jbrgates.JSONDecoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class HttpPostTest {
	public static void main(String[] args) throws IOException {
		HttpPostTest poster = new HttpPostTest();
		poster.sendPostRequest();
		// poster.testJson();
	}

	public void sendPostRequest() {
		HttpClient httpClient = new DefaultHttpClient();

		ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();

		try {
			formparams.add(new BasicNameValuePair("name", "zhh"));
			formparams.add(new BasicNameValuePair("x", "25"));
			formparams.add(new BasicNameValuePair("y", "20"));
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
					formparams, "UTF-8");
			HttpPost httppost = new HttpPost(
					"http://localhost/~huahan/PPServer/index.php/blog/index");
			httppost.setEntity(postEntity);
			HttpResponse response = httpClient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					String jsonString = EntityUtils.toString(entity);
					System.out.println(jsonString);
					Object obj = JSONValue.parse(jsonString);
					JSONArray array = (JSONArray) obj;
					System.out.println(array.toJSONString());
					System.out.println();

					// JSONDecoder jde = new JSONDecoder(jsonString);
					// TestJsonObj obj = (TestJsonObj) jde.decode();
					// System.out.println(obj.x);
					// System.out.println(obj.toString());
				} else {
					// Stream content out
				}
			}
			// handle response here...
		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public void testJson() {
		JSONObject obj = new JSONObject();
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", null);
		System.out.print(obj);
	}

	public void sendGetRequest() throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://localhost/~huahan/PPServer/index.php/blog/index");
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			long len = entity.getContentLength();
			if (len != -1 && len < 2048) {
				System.out.println(EntityUtils.toString(entity));
			} else {
				// Stream content out
			}
		}
	}
}

class TestJsonObj implements Serializable {
	public String name;
	public String x;
	public String y;
	public String des;

	public TestJsonObj(String name, String x, String y, String des) {
		System.out.println("in ");
		this.name = name;
		this.x = x;
		this.y = y;
		this.des = des;
	}

	public String toString() {
		return this.name + ", " + this.x + ", " + this.y + ", " + this.des;
	}
}