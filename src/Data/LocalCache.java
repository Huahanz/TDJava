package Data;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

public class LocalCache {
	HashMap<Object, LocalCacheNode> h = new HashMap<Object, LocalCacheNode>();

	public LocalCache() {

	}

	public boolean put(Object key, LocalCacheNode value) {
		this.h.put(key, value);
		return true;
	}

	public Object get(Object key) {
		LocalCacheNode valNode = this.h.get(key);
		if (valNode != null) {
			Time now = Time.now();
			if (now < valNode.timeExpire) {
				valNode.reconstruct();
			} else {
				valNode = null;
				this.h.put(key, valNode);
			}
		}
		return valNode;
	}
}
