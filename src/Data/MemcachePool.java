package Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Vector;

import Helpers.Config;

import net.spy.memcached.MemcachedClient;

public class MemcachePool {
	private static Vector<MemcachedClient> memcacheInstances = new Vector<MemcachedClient>();

	public static MemcachedClient getMemcache(int pool) {
		if (pool < 0 || pool > Config.MemServers.length) {
			return null;
		}
		MemcachedClient rst = null;
		if (pool < memcacheInstances.size()) {
			rst = memcacheInstances.get(pool);
			if (rst != null) {
				return rst;
			}
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

		memcacheInstances.set(pool, rst);
		return rst;
	}
}