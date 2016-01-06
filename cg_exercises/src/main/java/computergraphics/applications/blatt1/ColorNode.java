package computergraphics.applications.blatt1;

import com.jogamp.opengl.GL2;

import computergraphics.scenegraph.Node;;

public class ColorNode extends Node
{
	double r = 1;
	double g = 1;
	double b = 1;
	public ColorNode(double r, double g, double b)
	{
		// TODO Auto-generated constructor stub
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	@Override
	public void drawGl(GL2 gl)
	{
		//gl.glPushMatrix();

	    // Apply coloring
		gl.glColor3d(r, g, b);

	    // Restore original state
	    //gl.glPopMatrix();
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++)
		{
			getChildNode(childIndex).drawGl(gl);
		}
	}
}
