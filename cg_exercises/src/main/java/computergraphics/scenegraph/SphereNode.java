/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

/**
 * Geometry of a simple sphere.
 * 
 * @author Philipp Jenke
 */
public class SphereNode extends Node {

	/**
	 * Sphere radius.
	 */
	private double radius;
	private Vector3 center;

	/**
	 * Resolution (in one dimension) of the mesh.
	 */
	private int resolution;

	/**
	 * Constructor.
	 */
	public SphereNode(double radius, int resolution) {
		this.radius = radius;
		this.resolution = resolution;
	}
	
	public SphereNode(double radius, Vector3 center) {
		this.radius = radius;
		this.center = center;
	}
	
	@Override
	public IntersectionResult findIntersection(Node object, Ray3D r)
	{
		double p = (r.getPoint().multiply(r.getDirection()) * 2) - (center.multiply(r.getDirection()) * 2) / r.getDirection().multiply(r.getDirection());
		double q = ((r.getPoint().multiply(r.getPoint()) - (2 * r.getPoint().multiply(center)) + center.multiply(center)) - Math.pow(radius, 2)) / (r.getDirection().multiply(r.getDirection()));
		double lambda = Math.min((p/-2) + Math.sqrt(Math.pow(p, 2)/4 - q),(p/-2) - Math.sqrt(Math.pow(p, 2)/4 - q));

		if (Double.isNaN(lambda) || lambda <= 0)
			return null;
		IntersectionResult result = new IntersectionResult();
		result.point = r.getPoint().add(r.getDirection().multiply(lambda));
		result.object = this;
		result.normal = center.subtract(result.point).getNormalized();
		return result;
	}
	
	
	@Override
	public void drawGl(GL2 gl) {
		gl.glColor3d(0.75, 0.25, 0.25);
		GLU glu = new GLU();
		GLUquadric earth = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH);
		glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		final int slices = resolution;
		final int stacks = resolution;
		glu.gluSphere(earth, radius, slices, stacks);
	}


	public Vector3 getCenter()
	{
		return center;
	}


	public void setCenter(Vector3 center)
	{
		this.center = center;
	}

}
