package Wrapper;

import java.util.ArrayList;

public class InMapWrapper
{
	public int mapID;
	public ArrayList<Integer> pvpList;
	
	public InMapWrapper(int mapID, InPVPWrapper[] inPVPWrapper)
	{
		this.mapID = mapID;
	}
}