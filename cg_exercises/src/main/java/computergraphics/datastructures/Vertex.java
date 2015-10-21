/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.datastructures;

import computergraphics.math.Vector3;

/**
 * Representation of a vertex.
 * 
 * @author Philipp Jenke
 */
public class Vertex {

  /**
   * 3D position of the vertex.
   */
  private final Vector3 position = new Vector3(0, 0, 0);

  /**
   * (Normalized) normal direction of the vertex.
   */
  private Vector3 normal = new Vector3(1, 0, 0);

  /**
   * Color value at the vertex
   */
  private Vector3 color = new Vector3(0, 0, 0);

  /**
   * Reference to one of the outgoing half edges.
   */
  private HalfEdge halfEgde = null;

  /**
   * Constructor.
   * 
   * @param position
   *          Initial value for position.
   */
  public Vertex(Vector3 position) {
    this.position.copy(position);
  }

  /**
   * Constructor.
   * 
   * @param position
   *          Initial value for position.
   * @param normal
   *          Initial value for normal.
   */
  public Vertex(Vector3 position, Vector3 normal) {
    this.position.copy(position);
    this.normal.copy(normal);
  }

  /**
   * Constructor.
   * 
   * @param position
   *          Initial value for position.
   * @param normal
   *          Initial value for normal.
   */
  public Vertex(Vector3 position, Vector3 normal, Vector3 color) {
    this.position.copy(position);
    this.normal.copy(normal);
    this.color.copy(color);
  }

  public Vector3 getPosition() {
    return position;
  }

  public Vector3 getNormal() {
    return normal;
  }

  public Vector3 getColor() {
    return color;
  }

  public void setNormal(Vector3 normal) {
    this.normal.copy(normal);
  }

  public void setColor(Vector3 color) {
    this.color.copy(color);
  }

  public HalfEdge getHalfEdge() {
    return halfEgde;
  }

  public void setHalfEgde(HalfEdge halfEgde) {
    this.halfEgde = halfEgde;
  }

  @Override
  public String toString() {
    return "Vertex";
  }
}