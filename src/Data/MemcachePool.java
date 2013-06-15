package Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import Helpers.Config;

import net.spy.memcached.MemcachedClient;

public class MemcachePool {
	private static final ConcurrentHashMap<Integer, MemcachedClient> memcacheInstances = new ConcurrentHashMap<Integer, MemcachedClient>();

	public static MemcachedClient getMemcache(int pool) {
		MemcachedClient rst = memcacheInstances.get(pool);
		if (rst != null) {
			return rst;
		}

		String[] memInfo = Config.MemServers[pool];
		String host = memInfo[0];
		int port = Integer.parseInt(memInfo[1]);
		try {
			rst = new MemcachedClient(new InetSocketAddress(host, port));
			if (rst == null)
				return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		memcacheInstances.put(pool, rst);
		return rst;
	}
}