package computergraphics.applications.blatt4;

import computergraphics.math.Vector3;

public class ImplicitSphere implements ImplicitFunction
{
	//public final double r;
	private final double r_pow;
	public ImplicitSphere(double radius)
	{
		r_pow = Math.pow(radius,2);
	}
	
	public double getIso(Vector3 position)
	{
		return Math.pow(position.get(0),2) + Math.pow(position.get(1),2)  + Math.pow(position.get(2),2) - r_pow;
	}

}
