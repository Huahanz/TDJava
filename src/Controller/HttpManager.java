package Controller;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import balls.Ball;

public class HttpManager {
	public static String readTestOneSlotBalls() {
		return "";
	}

	public static synchronized String sendPostRequest(
			ArrayList<BasicNameValuePair> formparams, String postURL) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
					formparams, "UTF-8");
			HttpPost httppost = new HttpPost(postURL);
			httppost.setEntity(postEntity);
			HttpResponse response = httpClient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					String jsonString = EntityUtils.toString(entity);
					return jsonString;
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
		return null;
	}

}