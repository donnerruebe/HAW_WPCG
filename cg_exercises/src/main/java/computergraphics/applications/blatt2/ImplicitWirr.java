package computergraphics.applications.blatt2;

import computergraphics.applications.blatt4.ImplicitFunction;
import computergraphics.math.Vector3;

public class ImplicitWirr implements ImplicitFunction
{

	@Override
	public double getIso(Vector3 position)
	{
		double x = position.get(0);
		double y = position.get(1);
		double z = position.get(2);
		//return x + 2*y - 3*z + 1;
		return Math.pow(x, 2) * Math.pow(y, 2) + Math.pow(x, 2) * Math.pow(z, 2) + Math.pow(z, 2) * Math.pow(y, 2) + x*y*z;
		//return Math.pow((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) + Math.pow(R, 2)) - Math.pow(r, 2),2) - (4 * Math.pow(R, 2) * (Math.pow(x, 2) + Math.pow(y, 2)));
	}

}
