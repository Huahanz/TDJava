package Data;

import java.io.IOException;
import java.net.InetSocketAddress;

import Helpers.Config;

import net.spy.memcached.MemcachedClient;

public class MemcacheManager {
	MemcachedClient mem;
	public MemcacheManager(int pool) {
		mem = MemcachePool.getMemcache(pool);
	}

	public Object get(String key){
		Object r = mem.get(key);
		return r;
	}
	
	public void set(String key, Object val, int expiration){
		mem.set(key, expiration, val);
	}
}