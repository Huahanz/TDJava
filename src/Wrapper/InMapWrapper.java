package Wrapper;

public class InMapWrapper
{
	public int mapID;
	public InPVPWrapper[] inPVPWrapper;
	
	public InMapWrapper(int mapID, InPVPWrapper[] inPVPWrapper)
	{
		this.mapID = mapID;
		this.inPVPWrapper = inPVPWrapper;
	}
}