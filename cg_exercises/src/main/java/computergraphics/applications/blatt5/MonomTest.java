package computergraphics.applications.blatt5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import computergraphics.applications.blatt1.TranslationNode;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.SphereNode;

public class MonomTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void test()
	{
		MonomCurve curve = new MonomCurve();
		List<Vector3> points = new ArrayList<Vector3>();
		points.add(new Vector3(1, 1, 1));
		points.add(new Vector3(1, 2, 2));
		points.add(new Vector3(2, 0, 1));

		List<Vector3> cpoints = MonomCurve.interpolate(points);
		
		curve.addControlpoint(cpoints.get(0));
		curve.addControlpoint(cpoints.get(1));
		curve.addControlpoint(cpoints.get(2));

		assertEquals(curve.calculate(0), points.get(0));
		assertEquals(curve.calculate(0.5), points.get(1));
		assertEquals(curve.calculate(1), points.get(2));
	}

}
