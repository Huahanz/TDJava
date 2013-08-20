package Data;

import java.util.Date;

public abstract class LocalCacheNode implements ReconstructService{
	Object keyRef;
	Object valRef;
	Date timeExpire;

	private LocalCacheNode(Object key, Object val, int expiration) {
		this.keyRef = key;
		this.valRef = val;
		this.timeExpire = Time.now() + expiration;
	}

	public abstract Object reconstruct();
	
}