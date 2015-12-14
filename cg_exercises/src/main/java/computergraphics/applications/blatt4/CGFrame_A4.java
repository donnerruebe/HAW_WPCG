/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications.blatt4;

import computergraphics.applications.blatt2.HalfEdgeTriangleMeshNode;
import computergraphics.applications.blatt2.ImplicitWirr;
import computergraphics.applications.blatt2.ImplicitTorus;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame_A4 extends AbstractCGFrame {
	ShaderNode root = new ShaderNode(ShaderType.PHONG);

  private static final long serialVersionUID = 4257130065274995543L;

  /**
   * Constructor.
   */
  public CGFrame_A4(int timerInverval) {
    super(timerInverval);
    MarchingCubes s = new MarchingCubes(new ImplicitSphere(1));
    MarchingCubes t = new MarchingCubes(new ImplicitTorus(0.7,1));
    MarchingCubes w = new MarchingCubes(new ImplicitWirr());

    //root.addChild(new HalfEdgeTriangleMeshNode(w.mesh));
    root.addChild(new HalfEdgeTriangleMeshNode(t.mesh));
    //root.addChild(new HalfEdgeTriangleMeshNode(w.mesh));
    getRoot().addChild(root);

    
    //root.addChild(ground);
  }

  /*
   * (nicht-Javadoc)
   * 
   * @see computergrafik.framework.ComputergrafikFrame#timerTick()
   */
  @Override
  protected void timerTick() {

  }

  public void keyPressed(int keyCode) {
    //System.out.println("Key pressed: " + (char) keyCode);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // The timer ticks every 1000 ms.
    new CGFrame_A4(1000);
	  //new MarchingCubes();
  }
}
