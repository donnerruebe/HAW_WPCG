package computergraphics.applications.blatt5;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;

public class MonomCurve extends Curve
{

	@Override
	public Vector3 calculate(double t)
	{
		Vector3 sum = cList.get(0);
		for (int i = 1; i < cList.size(); i ++)
		{
			sum = sum.add(cList.get(i).multiply(Math.pow(t ,i)));
		}
		
		return sum;
	}
	
	public static List<Vector3> interpolate(List<Vector3> points)
	{
		List<Vector3> controllpoints = new ArrayList<Vector3>();
		if (points.size() != 3)
		{
			return null;
		}

		Vector3 c0 = points.get(0);
		Vector3 c1 = points.get(1).multiply(4).subtract(points.get(0).multiply(3)).subtract(points.get(2));
		Vector3 c2 = points.get(2).subtract(points.get(0)).subtract(c1);
		
		controllpoints.add(c0);
		controllpoints.add(c1);
		controllpoints.add(c2);
		return controllpoints;
	}

	@Override
	public Vector3 getTangente(double t)
	{
		Vector3 sum = new Vector3(0, 0, 0);
		for (int i = 0; i < cList.size(); i ++)
		{
			sum = sum.add(cList.get(i).multiply(Math.pow(t, i-1) * i));
		}
		return sum;
	}

}
