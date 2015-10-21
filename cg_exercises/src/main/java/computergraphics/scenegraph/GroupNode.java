/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

/**
 * Simple implementation of a Node, which only calls the draw methods of its
 * children.
 * 
 * @author Philipp Jenke
 */
public class GroupNode extends Node {

  @Override
  public void drawGl(GL2 gl) {
    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
      getChildNode(childIndex).drawGl(gl);
    }
  }
}
