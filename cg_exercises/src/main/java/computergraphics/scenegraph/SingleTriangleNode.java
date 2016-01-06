/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

/**
 * This node draws a simple triangle.
 * 
 * @author Philipp Jenke
 */
public class SingleTriangleNode extends Node {

  @Override
  public void drawGl(GL2 gl) {
    gl.glBegin(GL2.GL_TRIANGLES);
    gl.glNormal3f(0, 0, 1);
    gl.glColor3d(1.0, 0.0, 0.0);
    gl.glVertex3f(-0.5f, -0.5f, 0);
    gl.glNormal3f(0, 0, 1);
    gl.glColor3d(0.0, 1.0, 0.0);
    gl.glVertex3f(0.5f, -0.5f, 0);
    gl.glNormal3f(0, 0, 1);
    gl.glColor3d(0.0, 0.0, 1.0);
    gl.glVertex3f(0, 0.5f, 0);
    gl.glEnd();
  }
}
