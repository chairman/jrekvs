package io.jrekvs.hash;

/**
 * hash op
 * @author Tommy.z
 *
 */
public interface Assoc {
	
	public void assoc_init(int hashpower_init);
	
	public long assoc_find(String key,int nkey,long hv);
	
	public boolean assoc_insert(long addr,long hv);
	
	public void assoc_delete(String key,int nkey,long hv);
	
	public void printHashtable();

}
