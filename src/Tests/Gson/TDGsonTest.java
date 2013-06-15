package Tests.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import Wrapper.InEntry;

public class TDGsonTest {
	public static void main(String[] args) {
		TDGsonTest test = new TDGsonTest();
		test.testInWrapper();
	}

	public void testParseObj(){
		String json = "{\"class\":\"PlayerCtrl\",\"method\":\"add_player\",\"val\":" +
				"{\"player_id\":16374890,\"player\":{\"id\":16374890,\"player_id\":16374890," +
				"\"name\":\"Huahan\",\"udid\":\"947709878\",\"pvp_ids\":\"[]\",\"ball_ids\":\"[]\",\"ball_num\":0}}}";
		
	}
	public void testInWrapper() {
		String json = "{\"71263287081172992\":[\"assign_balls\",[{\"type_id\":\"0\"}," +
				"{\"type_id\":\"1\"}]],\"65109977630507008\":[\"assign_balls\"," +
				"[{\"type_id\":\"1\"},{\"type_id\":\"1\"}]]}";
		Gson gson = new Gson();
//		InMapWrapper rst = gson.fromJson(json, InMapWrapper.class);
//		InEntry e0 = new InEntry(2, 3);
//		InEntry e1 = new InEntry(2, 4);
//		InEntry[] es = new InEntry[2];
//		es[0] = e0;
//		es[1] = e1;
//		InPVPWrapper p = new InPVPWrapper(8, es);
//		InPVPWrapper[] ps = new InPVPWrapper[1];
//		ps[0] = p;
//		InMapWrapper x = new InMapWrapper(9, ps);
//		String xs = gson.toJson(x, InMapWrapper.class);
//		System.out.println(xs);
		LinkedTreeMap rst = (LinkedTreeMap) gson.fromJson(json, Object.class);
		Set entrySet = rst.keySet();
		Iterator it = entrySet.iterator();
		System.out.println("LinkedHashMap entries : ");
		while(it.hasNext()){
			String key = (String) it.next();
			System.out.println(key + " ");
			ArrayList arr = (ArrayList) rst.get(key);
			System.out.println(arr.get(0));
			ArrayList temparr = (ArrayList) arr.get(1);
//			LinkedTreeMap arrlt = (LinkedTreeMap)();
			Set entrySetArrlt = rst.keySet();
			Iterator itarr = entrySetArrlt.iterator();
			while(itarr.hasNext()){
				System.out.println(itarr.next() + ", ");
			}
		}
		System.out.println();
	}
}