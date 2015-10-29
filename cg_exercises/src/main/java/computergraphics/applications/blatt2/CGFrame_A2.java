/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications.blatt2;

import computergraphics.applications.blatt1.ColorNode;
import computergraphics.datastructures.ObjIO;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame_A2 extends AbstractCGFrame {
	ShaderNode root = new ShaderNode(ShaderType.PHONG);
    
  /**
   * 
   */
  private static final long serialVersionUID = 4257130065274995543L;

  /**
   * Constructor.
   */
	float zoom = 1;
  public CGFrame_A2(int timerInverval) {
    super(timerInverval);
    getRoot().addChild(root);
    
    ObjIO objImport = new ObjIO();
    HalfEdgeTriangleMesh mesh = new HalfEdgeTriangleMesh();
    
    
    objImport.einlesen("D:\\htdocs\\subworkspace\\Computergrafik\\meshes\\cow.obj", mesh);
    mesh.computeTriangleNormals();
    mesh.setOppositeHalfEdges();
    
    ColorNode cNode = new ColorNode(0.5, 0.5, 0);
    cNode.addChild(new HalfEdgeTriangleMeshNode(mesh));
    root.addChild(cNode);
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
    System.out.println("Key pressed: " + (char) keyCode);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // The timer ticks every 1000 ms.
    new CGFrame_A2(1000);
  }
}
