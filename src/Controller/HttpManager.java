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

public class HttpManager {
	
	public static String PHPBaseDispatcherUrl = "http://localhost/tdserver/index.php/dispatcher/index/";
	
	public static synchronized String sendPostRequest(
			ArrayList<BasicNameValuePair> formparams, String postURL) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
					formparams, "UTF-8");
			HttpPost httppost = new HttpPost(HttpManager.PHPBaseDispatcherUrl + postURL);
			httppost.setEntity(postEntity);
			HttpResponse response = httpClient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					String jsonString = EntityUtils.toString(entity);
					return jsonString;
				} else {
				}
			}
		} catch (Exception ex) {
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
	
	private static String getByMapPostUrl(String dir, String className, String method) {
		return dir + ":" + className + ":" + method;
	}

	public static String requestController(ArrayList<BasicNameValuePair> formparams, String dir, String className, String method){
		String postUrl = getByMapPostUrl(dir, className, method);
		return sendPostRequest(formparams, postUrl);
	}
}