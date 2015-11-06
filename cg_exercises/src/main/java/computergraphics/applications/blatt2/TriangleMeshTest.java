package computergraphics.applications.blatt2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

public class TriangleMeshTest
{
	HalfEdgeTriangleMesh mesh;

	@Before
	public void setUp() throws Exception
	{
		System.out.println("Setting up");
		mesh = new HalfEdgeTriangleMesh();
		
		Vertex v1 = new Vertex(new Vector3(1, 0, 0));
		Vertex v2 = new Vertex(new Vector3(0, 1, 0));
		Vertex v3= new Vertex(new Vector3(0, 0, 1));
		Vertex v4 = new Vertex(new Vector3(0, 0, 2));
		mesh.addVertex(v1);
		mesh.addVertex(v2);
		mesh.addVertex(v3);
		mesh.addVertex(v4);
	}

	@Test
	public void testAddTriangle()
	{
		assertEquals(4, mesh.getNumberOfVertices());
		mesh.addTriangle(0, 1, 2);
		assertEquals(1, mesh.getNumberOfTriangles());
		assertEquals(3, mesh.getHalfEdges().size());
		mesh.addTriangle(0, 1, 3);
		assertEquals(2, mesh.getNumberOfTriangles());
		assertEquals(6, mesh.getHalfEdges().size());
	 	
		//fail("Not yet implemented");
	}

	@Test
	public void testSetOppositeHalfEdges()
	{
		mesh.addTriangle(0, 1, 2);
		mesh.addTriangle(0, 1, 3);
		mesh.setOppositeHalfEdges();
		int countOpposites = 0;
		for (HalfEdge edge : mesh.getHalfEdges())
		{
			if (edge.getOpposite() != null)
			{
				countOpposites ++;
			}
		}
		System.out.println(mesh.getHalfEdges().size());
		assertEquals(2, countOpposites);
	}

}
