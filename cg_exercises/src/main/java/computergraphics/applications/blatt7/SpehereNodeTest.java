package computergraphics.applications.blatt7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.SphereNode;

public class SpehereNodeTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetIntersectionResult()
	{
		SphereNode sphere = new SphereNode(1, new Vector3(3, 0, 0));
		
		Ray3D ray = new Ray3D(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
		
		assertNotNull(sphere.findIntersection(null,ray));
		Vector3 schnitt = sphere.findIntersection(null,ray).point;
		
		// Schnittpunkt
		assertEquals(2, schnitt.get(0),0.00001);
		assertEquals(0, schnitt.get(1),0.00001);
		assertEquals(0, schnitt.get(2),0.00001);
		
		
		
		sphere.setCenter(new Vector3(3, 3, 3));
		ray = new Ray3D(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
		// Keine Schnittmenge
		assertNull(sphere.findIntersection(null,ray));
		
		
		ray = new Ray3D(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
		assertNotNull(sphere.findIntersection(null,ray));
		schnitt = sphere.findIntersection(null,ray).point;

		// Schnittpunkt
		assertEquals(3-Math.sqrt(1./3), schnitt.get(0),0.0001);
		assertEquals(3-Math.sqrt(1./3), schnitt.get(1),0.0001);
		assertEquals(3-Math.sqrt(1./3), schnitt.get(2),0.0001);
	}

}
