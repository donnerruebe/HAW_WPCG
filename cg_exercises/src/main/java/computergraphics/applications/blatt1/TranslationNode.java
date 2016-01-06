package computergraphics.applications.blatt1;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;

public class TranslationNode extends Node
{
	Vector3 translation;

	public TranslationNode(Vector3 translation)
	{
		// TODO Auto-generated constructor stub
		this.translation = translation;
	}

	@Override
	public void drawGl(GL2 gl)
	{
		// TODO Auto-generated method stub
		gl.glPushMatrix();

	    // Apply scaling
	    gl.glTranslated(translation.get(0), translation.get(1), translation.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	}

}
