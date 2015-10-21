/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.math;

/**
 * Representation of a vector in 3-space.
 * 
 * @author Philipp Jenke
 * 
 */
public class Vector3 {
  /**
   * Array containing the vector values.
   */
  private final double[] values = { 0, 0, 0 };

  /**
   * Constructor
   */
  public Vector3() {
  }

  /**
   * Constructor with initialization.
   */
  public Vector3(double x, double y, double z) {
    values[MathHelpers.INDEX_0] = x;
    values[MathHelpers.INDEX_1] = y;
    values[MathHelpers.INDEX_2] = z;
  }

  /**
   * Copy constructor.
   */
  public Vector3(Vector3 other) {
    for (int i = 0; i < MathHelpers.DIMENSION_3; i++) {
      values[i] = other.get(i);

    }
  }

  @Override
  public int hashCode() {
    return values.length;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Vector3)) {
      return false;
    }
    Vector3 otherVector = (Vector3) other;

    for (int i = 0; i < MathHelpers.DIMENSION_3; i++) {
      if (Math.abs(values[i] - otherVector.get(i)) > MathHelpers.EPSILON) {
        return false;
      }
    }

    return true;
  }

  /**
   * Scale vector.
   */
  public Vector3 multiply(final double s) {
    Vector3 result = new Vector3();
    for (int i = 0; i < MathHelpers.DIMENSION_3; i++) {
      result.set(i, values[i] * s);
    }
    return result;
  }

  /**
   * Get the squared norm.
   */
  public double getSqrNorm() {
    double sn = 0;
    for (int i = 0; i < MathHelpers.DIMENSION_3; i++) {
      sn += values[i] * values[i];
    }
    return sn;
  }

  /**
   * Get the norm (length) of the vector.
   */
  public double getNorm() {
    return Math.sqrt(getSqrNorm());
  }

  /**
   * Setter.
   */
  public void set(final int index, final double value) {
    values[index] = value;
  }

  /**
   * Getter.
   */
  public double get(final int index) {
    return values[index];
  }

  /**
   * Compute the cross product, return the result.
   */
  public Vector3 cross(final Vector3 other) {
    Vector3 result = new Vector3();
    result.set(
        MathHelpers.INDEX_0,
        get(MathHelpers.INDEX_1)
            * other.get(MathHelpers.INDEX_2)
            - get(MathHelpers.INDEX_2)
            * other.get(MathHelpers.INDEX_1));
    result.set(
        MathHelpers.INDEX_1,
        get(MathHelpers.INDEX_2)
            * other.get(MathHelpers.INDEX_0)
            - get(MathHelpers.INDEX_0)
            * other.get(MathHelpers.INDEX_2));
    result.set(
        MathHelpers.INDEX_2,
        get(MathHelpers.INDEX_0)
            * other.get(MathHelpers.INDEX_1)
            - get(MathHelpers.INDEX_1)
            * other.get(MathHelpers.INDEX_0));
    return result;
  }

  /**
   * Subtract another vector. Return the resulting vector.
   */
  public Vector3 subtract(Vector3 other) {
    Vector3 result = new Vector3();
    for (int i = 0; i < MathHelpers.DIMENSION_3; i++) {
      result.set(i, get(i) - other.get(i));
    }
    return result;
  }

  /**
   * Add another vector. Return the resulting vector.
   */
  public Vector3 add(Vector3 other) {
    Vector3 result = new Vector3();
    for (int i = 0; i < MathHelpers.DIMENSION_3; i++) {
      result.set(i, get(i) + other.get(i));
    }
    return result;
  }

  /**
   * Return a normalized version of the vector. This-vector is not altered.
   */
  public Vector3 getNormalized() {
    final double d = getNorm();
    if (Math.abs(d) < MathHelpers.EPSILON) {
      return new Vector3();
    }
    Vector3 normalizedVector =
        new Vector3(this.multiply(1.0 / d));
    return normalizedVector;
  }

  /**
   * Return the values as a double-array.
   */
  public double[] data() {
    return values;
  }

  /**
   * Return the values as a float-array.
   */
  public float[] floatData() {
    float[] floatData = new float[values.length];
    for (int i = 0; i < values.length; i++) {
      floatData[i] = (float) values[i];
    }
    return floatData;
  }

  /**
   * Compute the scalar product with another vector - return the result.
   */
  public double multiply(Vector3 other) {
    double result =
        get(0) * other.get(0) + get(1) * other.get(1)
            + get(2) * other.get(2);
    return result;
  }

  /**
   * Copy the values from another vector to the this-values-array.
   */
  public void copy(Vector3 other) {
    values[MathHelpers.INDEX_0] =
        other.get(MathHelpers.INDEX_0);
    values[MathHelpers.INDEX_1] =
        other.get(MathHelpers.INDEX_1);
    values[MathHelpers.INDEX_2] =
        other.get(MathHelpers.INDEX_2);
  }

  /**
   * Normalize the vector. This-vector is altered.
   */
  public void normalize() {
    double norm = getNorm();
    values[MathHelpers.INDEX_0] /= norm;
    values[MathHelpers.INDEX_1] /= norm;
    values[MathHelpers.INDEX_2] /= norm;
  }

  @Override
  public String toString() {
    return String.format("(%f, %f, %f)", values[0],
        values[1], values[2]);
  }
}
