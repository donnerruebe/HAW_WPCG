package computergraphics.applications.blatt2;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.scenegraph.Node;

public class HalfEdgeTriangleMeshNode extends Node
{
	private static int MESH_LIST = 1;
	HalfEdgeTriangleMesh mesh;
	boolean displayListCreated = false;
	

	public HalfEdgeTriangleMeshNode(HalfEdgeTriangleMesh mesh)
	{
		this.mesh = mesh;
	}
	
	/**
	 * Initialisiert eine Displayliste
	 */
	private void initDisplayList(GL2 gl)
	{
		System.out.println("Creating Display List");
		
		MESH_LIST = gl.glGenLists(1);
		System.out.println(MESH_LIST);
		gl.glNewList( MESH_LIST, GL2.GL_COMPILE );
		gl.glBegin(GL2.GL_TRIANGLES);
		for (int i = 0; i < mesh.getNumberOfTriangles(); i ++)
		{
			TriangleFacet f = mesh.getFacet(i);
			
			HalfEdge he = f.getHalfEdge();
			Vertex v = he.getStartVertex();
			//gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glNormal3d(f.getNormal().get(0), f.getNormal().get(1), f.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			
			
			he = he.getNext();
			v = he.getStartVertex();
			//gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			

			he = he.getNext();
			v = he.getStartVertex();
			//gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			
		}
		gl.glEnd();
		gl.glEndList();
		displayListCreated = true;
	}

	@Override
	public void drawGl(GL2 gl)
	{
		if (!displayListCreated)
		{
			initDisplayList(gl);
			System.out.println("Display List inited");
		}

		gl.glCallList(MESH_LIST);
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++)
		{
			getChildNode(childIndex).drawGl(gl);
		}
	}

}
