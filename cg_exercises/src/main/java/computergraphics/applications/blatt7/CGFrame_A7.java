/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications.blatt7;

import javax.swing.JFrame;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.framework.Camera;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.RootNode;
import computergraphics.scenegraph.SphereNode;


public class CGFrame_A7
{
	/**
	 * Constructor.
	 */
	public CGFrame_A7()
	{
		Node root = new RootNode();
		
		// 1. Param = X-Achse, 2. Y-Achse, 3. Z-Achse
		
//		Node sp1 = new SphereNode(1, new Vector3(1,0,0));
//		sp1.setColor(new Vector3(0,0,1));
//		
//		Node sp2 = new SphereNode(1, new Vector3(-1,0,0));
//		sp2.setColor(new Vector3(1,0,0));
//		sp2.setGlossiness(5);
//		sp2.setReflexion(0.2);
//		
//		Node sp3 = new SphereNode(0.5, new Vector3(-0.5,0,2));
//		sp3.setColor(new Vector3(0,1,0));
//		sp3.setGlossiness(30);
		
		Node sp1 = new SphereNode(1, new Vector3(2.5,0,0));
		sp1.setColor(new Vector3(1,0,0));
		
		Node sp2 = new SphereNode(1, new Vector3(0,0,0));
		sp2.setColor(new Vector3(1,0,0));
		sp2.setGlossiness(5);
		sp2.setReflexion(1);
		
		Node sp3 = new SphereNode(1, new Vector3(-2.5,0,0));
		sp3.setColor(new Vector3(0,1,1));
		sp3.setGlossiness(30);
		sp3.setReflexion(0.5);
		
		Node e1 = new EbeneNode(new Vector3(0,-1,0), new Vector3(0,-1,0));
		//e1.setColor(new Vector3(1,0,1));
		e1.setReflexion(0.1);

		root.addChild(sp1);
		root.addChild(sp2);
		root.addChild(sp3);
		root.addChild(e1);

		
		
		Camera c = new Camera();
		c.setEye(new Vector3(7,5,10));
		c.setRef(new Vector3(0,0,1));
		
		Raytracer tracer = new Raytracer(c, root);
		ImageViewer viewer = new ImageViewer(tracer.render(800, 640));
		viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	
	/**
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		new CGFrame_A7();
	}



	@Override
	protected void timerTick()
	{
		// TODO Auto-generated method stub
		
	}

}
