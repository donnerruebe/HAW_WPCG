
package computergraphics.applications.blatt6;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.framework.Camera;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.LightSource;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.SphereNode;

/**
 * Creates a raytraced image of the current scene.
 */
public class Raytracer
{

	/**
	 * Reference to the current camera.
	 */
	private final Camera camera;
	private final Node rootNode;
	
	private List<Node> children;
	
	//private final Vector3 light = new Vector3(0, 0, 4); 
	private LightSource light = new LightSource(new Vector3(-2,1,4),new Vector3(1,1,1));
	
	boolean debug = false;

	/**
	 * Constructor.
	 * 
	 * @param camera
	 *            Scene camera.
	 * @param rootNode
	 *            Root node of the scenegraph.
	 */
	public Raytracer(Camera camera, Node rootNode)
	{
		this.camera = camera;
		this.rootNode = rootNode;
	}

	/**
	 * Creates a raytraced image for the current view with the provided
	 * resolution. The opening angle in x-direction is grabbed from the camera,
	 * the opening angle in y-direction is computed accordingly.
	 * 
	 * @param resolutionX
	 *            X-Resolution of the created image.
	 * 
	 * @param resolutionX
	 *            Y-Resolution of the created image.
	 */
	public Image render(int resolutionX, int resolutionY)
	{
		children = rootNode.getAllChildren();

		
		
		BufferedImage image = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_RGB);

		Vector3 viewDirection = camera.getRef().subtract(camera.getEye()).getNormalized();
		Vector3 xDirection = viewDirection.cross(camera.getUp()).getNormalized();
		Vector3 yDirection = viewDirection.cross(xDirection).getNormalized();
		double openingAngleYScale = Math.sin(camera.getOpeningAngle() * Math.PI / 180.0);
		double openingAngleXScale = openingAngleYScale * (double) resolutionX / (double) resolutionY;

		for (int i = 0; i < resolutionX; i++)
		{
			double alpha = (double) i / (double) (resolutionX + 1) - 0.5;
			for (int j = 0; j < resolutionY; j++)
			{
				if (i == 219 && (j == 460)) debug = true; else debug = false;
				double beta = (double) j / (double) (resolutionY + 1) - 0.5;
				Vector3 rayDirection = viewDirection.add(xDirection.multiply(alpha * openingAngleXScale))
						.add(yDirection.multiply(beta * openingAngleYScale)).getNormalized();
				Ray3D ray = new Ray3D(camera.getEye(), rayDirection);

				Vector3 color = trace(ray, 0);

				// Adjust color boundaries
				for (int index = 0; index < 3; index++)
				{
					color.set(index, Math.max(0, Math.min(1, color.get(index))));
				}

				image.setRGB(i, j,
						new Color((int) (255 * color.get(0)), (int) (255 * color.get(1)), (int) (255 * color.get(2)))
								.getRGB());
			}
		}
		//Draw MiddlePoint
		image.setRGB(resolutionX/2, resolutionY/2, new Color(255,255,255).getRGB());
		return image;
	}

	/**
	 * Compute a color from tracing the ray into the scene.
	 * 
	 * @param ray
	 *            Ray which needs to be traced.
	 * @param recursion
	 *            Current recursion depth. Initial recursion depth of the rays
	 *            through the image plane is 0. This parameter is used to abort
	 *            the recursion.
	 * 
	 * @return Color in RGB. All values are in [0,1];
	 */
	private Vector3 trace(Ray3D ray, int recursion)
	{
		if (recursion > 2)
		{
			return new Vector3(0, 0, 0);
		}
		IntersectionResult real_result = null;;
		List<IntersectionResult> results = new ArrayList<IntersectionResult>();
		// min Distance zum herausfinden des Objekts mit der niedrigsten Distanz
		double dist = Double.MAX_VALUE;
		for (Node node: children) //int i = 0; i < rootNode.getNumberOfChildren(); i ++
		{
			//Node node = rootNode.getChildNode(i);
			if (node.getClass().equals(SphereNode.class) || node.getClass().equals(EbeneNode.class))// || node.getClass().equals(LightSource.class))
			{
				IntersectionResult result = node.findIntersection(node, ray);
				
				if (result != null)
				{
					// Distanz zum Schnittpunkt geringer als letzte gespeicherte
					double cur_dist = ray.getPoint().subtract(result.point).getSqrNorm(); 
					if (cur_dist < dist)
					{
						real_result = result;
						dist = cur_dist;
					}
					results.add(result);
				}
			}
		}
		
		if (real_result != null)
		{
			boolean shadow = false;
			double dist_to_light = (light.getPosition().getNorm() - real_result.point.getNorm()); 
			
			// Berechnung des Schattens
//			Vector3 erg = trace(new Ray3D(real_result.point, light.subtract(real_result.point).getNormalized()),recursion + 1);
//			if (erg.equals(other) != null)
//			{
//				//System.out.println("Blub");
//				traces ++;
//			}
			if (debug)
			{
				System.out.println("Strahl startet in " + ray.getPoint() + " in Richtung " + ray.getDirection());
				System.out.println("Schnittpunkt bei Objekt " + real_result.object);
				System.out.println("Schnittpunkt in Punkt " + real_result.point);
				System.out.println("Lichtquelle: " + light.getPosition());
				System.out.println("Ray-Richtung zur Lichtquelle: " + light.getPosition().subtract(real_result.point));
				//return new Vector3(1,0,0);
			}
			//IntersectionResult shadow_result = real_result.object.findIntersection(null, new Ray3D(real_result.point, light.getPosition().subtract(real_result.point).getNormalized()));
			
			//double shad_dist = Double.MAX_VALUE;
			//IntersectionResult shad_result = null;
			for (Node n: children)
			{

				if (!n.equals(real_result.object))
				{
					IntersectionResult shad_result_tmp = n.findIntersection(real_result.object, new Ray3D(real_result.point, light.getPosition().subtract(real_result.point).getNormalized()));
					if (shad_result_tmp != null)
					{
						
						double shad_dist_tmp = (shad_result_tmp.point.getNorm() - real_result.point.getNorm());
						if (shad_dist_tmp < dist_to_light)
						{
							if (debug)
							{
								System.out.println(real_result.object + " hat Schatten durch " + shad_result_tmp.object);
								
							}
							shadow = true;
							break;
						}
					}
				}
				
			}
			double shadow_influence = 0;
			if (shadow)
			{
				shadow_influence = 0.5;
				//return new Vector3(0,0,0);
			}
			if (debug)
				System.out.println("Shhadow Influence: " + shadow_influence);

			
			
			
			// Berechnung des Farbanteils
			int m = 20;
			Vector3 N = real_result.normal;
			Vector3 Vs = ray.getDirection().getNormalized();
			//Vector3 E = real_result.point.subtract(ray.getPoint()).getNormalized();
			Vector3 L = real_result.point.subtract(light.getPosition()).getNormalized();
			if (debug)
			{
				System.out.println("Normal: " + N);
				System.out.println("Vs: " + Vs);
				System.out.println("L: " + L);
			}
			
			Vector3 cDiff = new Vector3(0, 0, 0);
			double LN = L.multiply(N);
			if (LN > 0)
			{
				cDiff = (real_result.object.getColor()).multiply(LN);
			} else if (debug)
			{
				System.out.println("Wert LN zu klein: " + LN);
			}
			Vector3 R = L.subtract(N.multiply(2*LN));
			double RVs = R.multiply(Vs.multiply(-1)); //R.multiply(Vs); 
			
			Vector3 cSpec = new Vector3(0, 0, 0);
			if (RVs > 0)
			{
				cSpec = (new Vector3(1, 1, 1)).multiply(Math.pow(RVs, m)); //.multiply(new Vector3(1,1,1));
			} else if (debug)
			{
				System.out.println("Wert RVs zu klein: " + LN);
			}
			
			//return real_result.object.getColor().multiply(0.2).add(cDiff.add(cSpec)); // Zeige immer ein bisschen Farbe
			return cDiff.add(cSpec).multiply(1-shadow_influence);
		}
		// Your task
		return new Vector3(0, 0, 0);
	}

}