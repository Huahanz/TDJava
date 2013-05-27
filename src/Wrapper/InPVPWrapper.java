package Wrapper;

/**
 * 
 *
 */
public class InPVPWrapper
{
	public int pvpID;
	public InEntry[] inEntries;
	public InPVPWrapper(int pvpID, InEntry[] inEntries)
	{
		this.pvpID = pvpID;
		this.inEntries = inEntries;
	}
}