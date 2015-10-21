/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.GroupNode;
import computergraphics.scenegraph.RotateNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslateNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame extends AbstractCGFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 4257130065274995543L;
  private Float richtung = new Float(0);
  
  private RotateNode rsphere;
  
  /**
   * Constructor.
   */
  public CGFrame(int timerInverval) {
    super(timerInverval);

    
    // Shader node does the lighting computation
    ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
    getRoot().addChild(shaderNode);

    CuboidNode erde = new CuboidNode(10, .1, 10);
    shaderNode.addChild(erde);
    
    getRoot().addLight(new Vector3(3 , 3, 3),new Vector3(0,1,1));
    // Simple triangle
    SingleTriangleNode triangleNode = new SingleTriangleNode();
    ScaleNode scaledTriangleNode = new ScaleNode(new Vector3(1.5, .4, 1));
    scaledTriangleNode.addChild(triangleNode);
    TranslateNode ttri = new TranslateNode(new Vector3(0, 1, -2));
    ttri.addChild(scaledTriangleNode);
    shaderNode.addChild(ttri);

    // Sphere
    SphereNode sphereNode = new SphereNode(0.2, 10);
    TranslateNode tsphere = new TranslateNode(new Vector3(5, 1, 0));
    rsphere = new RotateNode(this.richtung,new Vector3(0, 1, 0.4));
    tsphere.addChild(sphereNode);
    rsphere.addChild(tsphere);
    shaderNode.addChild(rsphere);
    
    //TREE_GROUP
    CuboidNode stem = new CuboidNode(.2, .9, .2);
    TranslateNode tstem = new TranslateNode(new Vector3(0, .45, 0));
    SphereNode crwn = new SphereNode(.25,20);
    TranslateNode tcrwn = new TranslateNode(new Vector3(0, .8, 0));
    GroupNode tree = new GroupNode();
    tstem.addChild(stem);
    tcrwn.addChild(crwn);
    tree.addChild(tstem);
    tree.addChild(tcrwn);
    
    shaderNode.addChild(tree);
    
    for(int i=0; i<70; i++){

        TranslateNode tmptn = new TranslateNode(new Vector3((Math.random()-.5)*10, 0, (Math.random()-.5)*10));
        tmptn.addChild(tree);
    	shaderNode.addChild(tmptn);
    }
    
  }

  /*
   * (nicht-Javadoc)
   * 
   * @see computergrafik.framework.ComputergrafikFrame#timerTick()
   */
  @Override
  protected void timerTick() {
    //System.out.println("Tick" + this.richtung);
    this.richtung += 5;
    this.richtung %= 360;
    rsphere.set_rotate(this.richtung);
  }

  public void keyPressed(int keyCode) {
    System.out.println("Key pressed: " + (char) keyCode);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // The timer ticks every 1000 ms.
    new CGFrame(100);
  }
}
