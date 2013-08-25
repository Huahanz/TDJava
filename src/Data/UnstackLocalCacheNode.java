package Data;

public class UnstackLocalCacheNode extends LocalCacheNode{

	public UnstackLocalCacheNode(Object key, Object val, int expire){
		super(key, val, expire);
	}
	public Object reconstruct() {
		return null;
	}

}