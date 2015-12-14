package computergraphics.applications.blatt5;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;

public abstract class Curve
{
	protected final List<Vector3> cList = new ArrayList<Vector3>();
	
	public void addControlpoint(Vector3 p)
	{
		cList.add(p);
	}
	
	public List<Vector3> getControllPoints()
	{
		return cList;
	}
	
	public int getGrad()
	{
		return cList.size();
	}
	
	
	public abstract Vector3 calculate(double t);
	
	public abstract Vector3 getTangente(double t);

}
