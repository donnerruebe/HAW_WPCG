package computergraphics.applications.blatt7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

public class EbeneNodeTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetIntersectionResult()
	{
		EbeneNode ebene = new EbeneNode(new Vector3(2, 2, 0), new Vector3(1, -1, 0));
		Ray3D strahl = new Ray3D(new Vector3(5,1,0), new Vector3(-1, 3, 0));
		
		Vector3 schnitt = ebene.findIntersection(null,strahl).point;
		
		assertEquals(4, schnitt.get(0),0.00001);
		assertEquals(4, schnitt.get(1),0.00001);
		assertEquals(0, schnitt.get(2),0.00001);
		//System.out.println(schnitt.get(0) + " : " + schnitt.get(1) + " : " + schnitt.get(2));
	}

}
