	/**
	 * Jan Rübbelke RÜBE
	 * Rübesystems
	 * 
	 * Base framework for "WP Computergrafik".
	 */
	package computergraphics.scenegraph;

	import com.jogamp.opengl.GL2;

	import computergraphics.math.Vector3;

public class RotateNode extends Node{




	  /**
	   * Scaling factors in x-, y- and z-direction.
	   */
	  private final Vector3 rotate = new Vector3(1, 1, 1);
	  private Float angle = new Float(0);

	  /**
	   * Constructor.
	   */
	  public RotateNode(Float angle, Vector3 translate) {
	    this.rotate.copy(translate);
	    this.angle =angle;
	  }
	  
	  public void set_rotate(float a) {
		  this.angle=a;		
	}

	  @Override
	  public void drawGl(GL2 gl) {
	    // Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply scaling
	    gl.glRotatef(angle, (float) rotate.get(0), (float) rotate.get(1),
	        (float) rotate.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	  }

	
}
