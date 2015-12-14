package computergraphics.applications.blatt5;

import computergraphics.math.Vector3;

public class BezierCurve extends Curve
{

	@Override
	public Vector3 calculate(double t)
	{
		int n = getGrad();
		Vector3 sum = new Vector3(0, 0, 0);
		for (int i = 0; i < n; i ++)
		{
			sum = sum.add(cList.get(i).multiply(B(n-1,i,t)));
		}
		return sum;
	}
	
	private double B(int n, int i, double t)
	{
		return (Fakultaet.f(n)/(Fakultaet.f(i)*Fakultaet.f(n-i))) * Math.pow(t, i) * Math.pow(1-t, n-i);
	}

	
	private double B_Abl(int n, int i, double t)
	{
		if (i == 0 || n == i)
		{
			return 0;
		}
		return n * (B(n-1,i-1,t) - B(n-1,i,t));
	}

	@Override
	public Vector3 getTangente(double t)
	{
		int n = getGrad();
		Vector3 sum = new Vector3(0, 0, 0);
		for (int i = 0; i < n; i ++)
		{
			sum = sum.add(cList.get(i).multiply(B_Abl(n-1,i,t)));
		}
		return sum;
	}

}
