package computergraphics.applications.blatt1;

import com.jogamp.opengl.GL2;

import computergraphics.scenegraph.Node;;

public class SingleColoredTriangleNode extends Node
{
	double r = 1;
	double g = 1;
	double b = 1;
	public SingleColoredTriangleNode(double r, double g, double b)
	{
		// TODO Auto-generated constructor stub
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	@Override
	  public void drawGl(GL2 gl) {
	    gl.glBegin(GL2.GL_TRIANGLES);
	    gl.glNormal3f(0, 0, 1);
	    gl.glColor3d(r, g, b);
	    gl.glVertex3f(-0.5f, -0.5f, 0);
	    gl.glNormal3f(0, 0, 1);
	    gl.glVertex3f(0.5f, -0.5f, 0);
	    gl.glNormal3f(0, 0, 1);
	    gl.glVertex3f(0, 0.5f, 0);
	    gl.glEnd();
	  }
}
