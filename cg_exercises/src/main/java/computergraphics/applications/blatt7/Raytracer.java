
package computergraphics.applications.blatt7;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
	// Anteil, um den der Schatten die Farbe verdunkelt. 0=Garnicht, 1=Komplett=Schwarz
	private static final double SHADOW_INFLUENCE = 0.5;
	// Liste aller Kinder des rootKnotens
	private List<Node> children;
	// Lichtquelle der Szene
	private LightSource light = new LightSource(new Vector3(-2,3,4),new Vector3(1,1,1));
	/**
	 * Reference to the current camera.
	 */
	private final Camera camera;


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
		// Ermittle alle Kinder und Kindeskinder des Wurzelknotens
		children = rootNode.getAllChildren();
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
		// Abbruch der Rekursion
		if (recursion > 2)
		{
			return new Vector3(0, 0, 0);
		}
		
		// Schnittpunkt zum nähesten Objekt
		IntersectionResult schnittpunkt = null;
		// Distanz des Schnittpunktes mit dem letzten Objekt 
		double dist = Double.MAX_VALUE;
		// Durchlaufe alle Objekte der Szene die vom Typ Sphere oder Ebene sind (da nur die implementiert)
		for (Node node: children)
		{
			if (node.getClass().equals(SphereNode.class) || node.getClass().equals(EbeneNode.class))
			{
				IntersectionResult result = node.findIntersection(node, ray);
				
				if (result != null)
				{
					// Distanz zum Schnittpunkt geringer als letzte gespeicherte
					double cur_dist = ray.getPoint().subtract(result.point).getSqrNorm(); 
					if (cur_dist < dist)
					{
						schnittpunkt = result;
						dist = cur_dist;
					}
				}
			}
		}
		
		if (schnittpunkt != null)
		{
			
			// Berechnung des Schattens
			double shadow_influence = getShadow(schnittpunkt);

			/* 
			 * Berechnen der Farben
			 */

			Vector3 farbe = schnittpunkt.object.getColor();
			// Bei einer Ebene Farbe des Schachbrettkaros ermitteln
			if (schnittpunkt.object.getClass().equals(EbeneNode.class))
			{
				farbe = ((EbeneNode)schnittpunkt.object).getChessboardColor(schnittpunkt.point);
			}
			
			farbe = getLightningColor(schnittpunkt, ray, farbe);
			
			// Einfluss des Schattens mit berechnen
			farbe = farbe.multiply(1-shadow_influence);

			/*
			 * Berechne Reflektion
			 */
			if (schnittpunkt.object.getReflexion() > 0)
			{
				// Farbanteil je nach Reflektiongrad herausnehmen
				farbe = farbe.multiply(1.-schnittpunkt.object.getReflexion());
				// Ideal reflektierter Vektor
				Vector3 ideal_reflect = ray.getDirection().subtract(schnittpunkt.normal.multiply(2*ray.getDirection().multiply(schnittpunkt.normal)));
				// Reflektierte durch erneuten Trace berechnen
				Vector3 reflected_color = trace(new Ray3D(schnittpunkt.point,ideal_reflect), recursion + 1);
				// Reflektierte Farbe mit deren Anteil multiplizieren (1 = Voll = Kein eigener Farbanteil)
				reflected_color = reflected_color.multiply(schnittpunkt.object.getReflexion());
				// Reflektierte Farbe hinzufuegen
				farbe = farbe.add(reflected_color);
			}
			
			/*
			 * Gib Farbe aus
			 */
			return farbe;
		}
		// Your task
		return new Vector3(0, 0, 0);
	}
	
	private Vector3 getLightningColor(IntersectionResult schnittpunkt, Ray3D ray, Vector3 objectColor)
	{
		// Hilfsvariablen
		int m = schnittpunkt.object.getGlossiness();
		// Für diffus
		Vector3 N = schnittpunkt.normal;
		Vector3 Vs = ray.getDirection().getNormalized();
		Vector3 L = schnittpunkt.point.subtract(light.getPosition()).getNormalized();
		// Für spekular
		double LN = L.multiply(N);
		Vector3 R = L.subtract(N.multiply(2*LN));
		double RVs = R.multiply(Vs.multiply(-1));
		
		Vector3 cDiff = new Vector3(0, 0, 0); // Initial
		Vector3 cSpec = new Vector3(0, 0, 0); // Initial
		
		if (LN > 0)
		{
			cDiff = objectColor.multiply(LN);
		}
		
		if (RVs > 0)
		{
			cSpec = (new Vector3(1, 1, 1)).multiply(Math.pow(RVs, m)); //.multiply(new Vector3(1,1,1));
		}
		return cDiff.add(cSpec);
	}

	/**
	 * Berechnet den Schatteneinfall auf einen Punkt
	 * @param schnittpunkt
	 * @return Einfluss des Schattens
	 */
	private double getShadow(IntersectionResult schnittpunkt)
	{
		double dist_to_light = (light.getPosition().getNorm() - schnittpunkt.point.getNorm()); 
		for (Node n: children)
		{

			if (!n.equals(schnittpunkt.object))
			{
				IntersectionResult shad_result_tmp = n.findIntersection(schnittpunkt.object, new Ray3D(schnittpunkt.point, light.getPosition().subtract(schnittpunkt.point).getNormalized()));
				if (shad_result_tmp != null)
				{
					
					double shad_dist_tmp = (shad_result_tmp.point.getNorm() - schnittpunkt.point.getNorm());
					if (shad_dist_tmp < dist_to_light)
					{
						// Objekt liegt im Schatten
						return SHADOW_INFLUENCE;
					}
				}
			}
			
		}
		return 0;
	}
}