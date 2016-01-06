package computergraphics.applications.blatt2;

import computergraphics.applications.blatt4.ImplicitFunction;
import computergraphics.math.Vector3;

public class ImplicitTorus implements ImplicitFunction
{
	private final double r;
	private final double R;
	
	public ImplicitTorus(double r, double R)
	{
		// TODO Auto-generated constructor stub
		this.r = r;
		this.R = R;
	}

	@Override
	public double getIso(Vector3 position)
	{
		double x = position.get(0);
		double y = position.get(1);
		double z = position.get(2);
		return Math.pow((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) + Math.pow(R, 2)) - Math.pow(r, 2),2) - (4 * Math.pow(R, 2) * (Math.pow(x, 2) + Math.pow(y, 2)));
	}

}
