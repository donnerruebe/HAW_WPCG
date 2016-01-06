/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications.blatt6;

import javax.swing.JFrame;

import computergraphics.framework.Camera;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.RootNode;
import computergraphics.scenegraph.SphereNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame_A6 //extends AbstractCGFrame
{
	/**
	 * Constructor.
	 */
	public CGFrame_A6()
	{

		Node root = new RootNode();
		// 1. Param = X-Achse, 2. Y-Achse, 3. Z-Achse
		
		Node sp1 = new SphereNode(1, new Vector3(1,0,0));
		sp1.setColor(new Vector3(0,0,1));
		//sp1.setCenter();
		
		Node sp2 = new SphereNode(1, new Vector3(-1,0,0));
		sp2.setColor(new Vector3(1,0,0));
		//sp2.setCenter();
		
		Node sp3 = new SphereNode(0.5, new Vector3(-0.5,0,2));
		sp3.setColor(new Vector3(0,1,0));
		//sp3.setCenter();
		
		Node e1 = new EbeneNode(new Vector3(0,-1,0), new Vector3(0,-1,0));
		e1.setColor(new Vector3(0,1,1));
		
		
		root.addChild(sp1);
		root.addChild(sp2);
		root.addChild(sp3);
		root.addChild(e1);
		

		System.out.println("SP1: " + sp1);
		System.out.println("SP2: " + sp2);
		System.out.println("SP3: " + sp3);
		
		//getRoot().addChild(new LightSource(new Vector3(-2,0,4),new Vector3(1,1,1)));
		Camera c = new Camera();
		c.setEye(new Vector3(0,1,10));
		c.setRef(new Vector3(0,0,1));
		//c.setUp(new Vector3(0,1,0));
		
		Raytracer tracer = new Raytracer(c, root);
		ImageViewer viewer = new ImageViewer(tracer.render(800, 640));
		viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//ShaderNode root = new ShaderNode(ShaderType.PHONG);

	
	/**
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		new CGFrame_A6();
	}

}
