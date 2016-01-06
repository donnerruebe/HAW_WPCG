package computergraphics.applications.blatt6;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;;

public class EbeneNode extends Node
{
	Vector3 point;
	Vector3 normal;
	

	public EbeneNode(Vector3 point, Vector3 normal)
	{
		super();
		this.point = point;
		this.normal = normal;
	}
	
	@Override
	public IntersectionResult findIntersection(Node object, Ray3D r)
	{
		double lambda = (this.normal.multiply(this.point) - this.normal.multiply(r.getPoint())) / this.normal.multiply(r.getDirection());
		if (Double.isNaN(lambda) || lambda < 0)
		{
			return null;
		}
		//double lambda = (normal.multiply(point) - normal.multiply(r.getPoint())) / (normal.multiply(r.getDirection()));
		Vector3 point = r.getPoint().add(r.getDirection().multiply(lambda));
		IntersectionResult result = new IntersectionResult();
		result.point = point;
		result.object = this;
		result.normal = this.normal.getNormalized();
		return result;
		
	}


	@Override
	public void drawGl(GL2 gl)
	{
		
	}

}
