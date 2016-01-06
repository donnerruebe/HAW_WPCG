package computergraphics.applications.blatt1;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;

public class RotationNode extends Node
{
	public enum AXIS { X_AXIS, Y_AXIS, Z_AXIS };
	public float angle;
	public final Vector3 axis;

	public RotationNode(Vector3 axis, float angle)
	{
		this.angle = angle;
		this.axis = axis;
	}

	@Override
	public void drawGl(GL2 gl)
	{
		gl.glPushMatrix();

	    // Apply scaling
		gl.glRotated(angle,axis.get(0), axis.get(1), axis.get(2));
	    
//		gl.glScalef((float) scale.get(0), (float) scale.get(1),
//	        (float) scale.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	}

}
