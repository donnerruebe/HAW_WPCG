/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework;

import computergraphics.math.Matrix3;
import computergraphics.math.Vector3;

/**
 * Represents a camera.
 * 
 * @author Philipp Jenke
 */
public class Camera {

  /**
   * Factor used, when zoomin in and out.
   */
  private static final double ZOOM_FACTOR = 0.05;

  /**
   * Position of the eye.
   */
  private Vector3 eye;

  /**
   * Reference point of the camera (look at)
   */
  private Vector3 ref;

  /**
   * Up-vector of the camera.
   */
  private Vector3 up;

  /**
   * Opening angle of the camera.
   */
  private double openingAngleY = 45.0;

  /**
   * Constructor.
   */
  public Camera() {
    eye = new Vector3(0, 0, 5);
    ref = new Vector3(0, 0, 0);
    up = new Vector3(0, 1, 0);
  }

  /**
   * Getter.
   */
  public Vector3 getEye() {
    return eye;
  }

  /**
   * Getter.
   */
  public Vector3 getRef() {
    return ref;
  }

  /**
   * Getter.
   */
  public Vector3 getUp() {
    return up;
  }

  /**
   * Setter.
   */
  public void setEye(Vector3 e) {
    eye = new Vector3(e);
  }

  /**
   * Setter.
   */
  public void setRef(Vector3 e) {
    ref = new Vector3(e);
  }

  /**
   * Setter.
   */
  public void setUp(Vector3 e) {
    up = new Vector3(e);
  }

  /**
   * Rotate the camera around the up vector and the eye position.
   */
  public void rotateVertically(final double angle) {
    // Normalize direction
    Vector3 oldDirection = eye.subtract(ref);
    double length = oldDirection.getNorm();
    oldDirection = oldDirection.multiply(1.0 / length);

    // Apply rotation
    Matrix3 rotationMatrix = Matrix3.getRotationMatrix(up, angle);
    Vector3 newDirection =
        rotationMatrix.multiply(oldDirection).getNormalized();
    Vector3 newEye = ref.add(newDirection.multiply(length));

    // Assign new coordinate frame
    eye = newEye;
  }

  /**
   * Rotate the camera around a horizontal axis.
   */
  public void rotateHorizontally(final double angle) {
    // Normalize direction
    Vector3 oldDirection = eye.subtract(ref);
    double length = oldDirection.getNorm();
    oldDirection = oldDirection.getNormalized();

    // Compute rotation axis
    Vector3 axis = oldDirection.cross(up).getNormalized();

    // Apply rotation
    Matrix3 rotationMatrix = Matrix3.getRotationMatrix(axis, angle);
    Vector3 newDirection =
        rotationMatrix.multiply(oldDirection).getNormalized();
    Vector3 newEye = ref.add(newDirection.multiply(length));

    // Assign new coordinate frame
    eye = newEye;
    up = new Vector3(axis.cross(newDirection)).getNormalized();
  }

  /**
   * Zooming
   * 
   * @param d
   */
  public void zoom(double d) {
    if (d > 0) {
      eye = eye.add(ref.subtract(eye).multiply(ZOOM_FACTOR));
    } else if (d < 0) {
      eye = eye.subtract(ref.subtract(eye).multiply(ZOOM_FACTOR));
    }
  }

  /**
   * Getter.
   */
  public double getOpeningAngle() {
    return openingAngleY;
  }
}
