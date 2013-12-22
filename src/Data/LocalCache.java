package Data;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;

public class LocalCache {
	static ConcurrentHashMap<Object, LocalCacheNode> h = new ConcurrentHashMap<Object, LocalCacheNode>();
	static BlockingQueue bq = new BlockingQueue();
	static Condition cnd = new Condition();
	private LocalCache() {

	}

	public static boolean put(Object key, LocalCacheNode value) {
		h.put(key, value);
		return true;
	}

	public static Object get(Object key) {
		LocalCacheNode valNode = h.get(key);
		Object val = valNode.valRef;
		if (val != null) {
			Date now = new Date();
			if (now.compareTo( valNode.timeExpire) < 0) {
				valNode.reconstruct();
			} else {
				valNode = null;
				h.put(key, valNode);
			}
		}
		return val;
	}
}
