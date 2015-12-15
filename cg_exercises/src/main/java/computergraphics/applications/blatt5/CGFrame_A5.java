/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications.blatt5;

import java.util.ArrayList;
import java.util.List;

import computergraphics.applications.blatt1.TranslationNode;
import computergraphics.applications.blatt2.HalfEdgeTriangleMeshNode;
import computergraphics.applications.blatt2.ImplicitWirr;
import computergraphics.applications.blatt2.ImplicitTorus;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.SphereNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame_A5 extends AbstractCGFrame
{
	ShaderNode root = new ShaderNode(ShaderType.PHONG);
	double tangenteValue = 0.5;
	Curve curve;
	CurveNode tangente;

	private static final long serialVersionUID = 4257130065274995543L;

	/**
	 * Constructor.
	 */
	public CGFrame_A5(int timerInverval)
	{
		super(timerInverval);

		if (timerInverval < 0)
		{
			curve = new BezierCurve();
			curve.addControlpoint(new Vector3(0, 1, 0));
			curve.addControlpoint(new Vector3(3, 2, 1));
			curve.addControlpoint(new Vector3(1, 3, 3));
			curve.addControlpoint(new Vector3(1, 0, 0));
			
			CurveNode n = new CurveNode(curve);
			root.addChild(n);

			for (Vector3 c : curve.getControllPoints())
			{
				TranslationNode tn = new TranslationNode(c);
				tn.addChild(new SphereNode(.1, 15));
				root.addChild(tn);
			}

		}
		else
		{
			List<Vector3> points = new ArrayList<Vector3>();
//			points.add(new Vector3(0, 0, 1));
//			points.add(new Vector3(1, 2, 1));
//			points.add(new Vector3(2, 0, 1));
			

			points.add(new Vector3(1, 1, 1));
			points.add(new Vector3(1, 2, 2));
			points.add(new Vector3(2, 0, 1));

			List<Vector3> cpoints = MonomCurve.interpolate(points);
			curve = new MonomCurve();
			curve.addControlpoint(cpoints.get(0));
			curve.addControlpoint(cpoints.get(1));
			curve.addControlpoint(cpoints.get(2));
			CurveNode n1 = new CurveNode(curve);

			for (Vector3 c : curve.getControllPoints())
			{
				TranslationNode tn = new TranslationNode(c);
				tn.addChild(new SphereNode(.1, 15));
				root.addChild(tn);
			}

			for (Vector3 c : points)
			{
				TranslationNode tn = new TranslationNode(c);
				tn.addChild(new SphereNode(.05, 15));
				root.addChild(tn);
			}
			root.addChild(n1);

		}
		getRoot().addChild(root);

	}

	@Override
	protected void timerTick()
	{
		if (tangente != null)
		{
			root.removeChild(tangente);
		}

		BezierCurve bct = new BezierCurve();
		bct.addControlpoint(curve.calculate(tangenteValue));
		bct.addControlpoint(curve.calculate(tangenteValue).add(curve.getTangente(tangenteValue)));
		tangente = new CurveNode(bct);
		root.addChild(tangente);
	}

	public void keyPressed(int keyCode)
	{
		if ((char) keyCode == 'J' && tangenteValue > 0)
		{
			System.out.println("j");
			tangenteValue -= 0.1;
		}
		else if ((char) keyCode == 'K' && tangenteValue < 1)
		{
			System.out.println("K");
			tangenteValue += 0.1;
		}
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		// The timer ticks every 1000 ms.
		new CGFrame_A5(100);
		// new MarchingCubes();
	}
}
