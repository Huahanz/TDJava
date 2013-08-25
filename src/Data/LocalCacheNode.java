package Data;

import java.util.Date;
import java.util.GregorianCalendar;

public abstract class LocalCacheNode implements ReconstructService{
	Object keyRef;
	Object valRef;
	Date timeExpire;

	protected LocalCacheNode(Object key, Object val, int expiration) {
		this.keyRef = key;
		this.valRef = val;
		Date now = new Date();
		this.timeExpire = new Date(now.getTime() + expiration);
	}

	public abstract Object reconstruct();
	
}