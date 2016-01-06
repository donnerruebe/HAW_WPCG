package computergraphics.applications.blatt5;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.math.Vector3;

public class BezierTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void test()
	{
		BezierCurve curve = new BezierCurve();
		Vector3 p1 = new Vector3(0, 1, 0);
		Vector3 p2 = new Vector3(3, 2, 1);
		Vector3 p3 = new Vector3(1, 3, 3);
		
		curve = new BezierCurve();
		curve.addControlpoint(p1);
		curve.addControlpoint(p2);
		
		// Test mit zwei Punkten
		assertEquals(curve.calculate(0.5), p1.add(p2).multiply(0.5));
		
		curve = new BezierCurve();
		curve.addControlpoint(p1);
		curve.addControlpoint(p2);
		curve.addControlpoint(p3);
		
		// Test mit drei Punkten (Mittelpunkt aller Punkte)
		assertEquals(curve.calculate(0.5), p1.add(p2).multiply(0.5).add(p2.add(p3).multiply(0.5)).multiply(0.5));
		
	}

}
