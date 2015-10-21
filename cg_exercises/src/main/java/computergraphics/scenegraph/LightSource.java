package computergraphics.scenegraph;

import computergraphics.math.Vector3;

/**
 * Represents a point light source in 3-space.
 * 
 * @author Philipp Jenke
 */
public class LightSource {

  /**
   * Position of the light source.
   */
  private Vector3 position = new Vector3();

  /**
   * Color of the light source.
   */
  private Vector3 color = new Vector3();

  public LightSource(Vector3 position, Vector3 color) {
    this.position.copy(position);
    this.color.copy(color);
  }

  public Vector3 getPosition() {
    return position;
  }

  public Vector3 getColor() {
    return color;
  }

}
