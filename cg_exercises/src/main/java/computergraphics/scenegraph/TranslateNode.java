	/**
	 * Jan Rübbelke RÜBE
	 * Rübesystems
	 * 
	 * Base framework for "WP Computergrafik".
	 */
	package computergraphics.scenegraph;

	import com.jogamp.opengl.GL2;

	import computergraphics.math.Vector3;

public class TranslateNode extends Node{




	  /**
	   * Scaling factors in x-, y- and z-direction.
	   */
	  private final Vector3 translate = new Vector3(1, 1, 1);

	  /**
	   * Constructor.
	   */
	  public TranslateNode(Vector3 translate) {
	    this.translate.copy(translate);
	  }

	  @Override
	  public void drawGl(GL2 gl) {
	    // Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply scaling
	    gl.glTranslatef((float) translate.get(0), (float) translate.get(1),
	        (float) translate.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	  }

	
}
