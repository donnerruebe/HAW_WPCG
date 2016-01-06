/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import computergraphics.scenegraph.RootNode;

/**
 * Central frame for all applications - derive from this class.
 * 
 * @author Philipp Jenke
 */
public abstract class AbstractCGFrame extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 2322862744369751274L;

  /**
   * 3D view object.
   */
  private final View3D view;

  /**
   * Timer object to create a game loop.
   */
  private Timer timer = new Timer();

  /**
   * Root node of the scene graph
   */
  private RootNode root = new RootNode();

  /**
   * Constructor
   */
  public AbstractCGFrame(int timerInterval) {
    view = new View3D(this);
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        timerTick();
      }
    }, timerInterval, timerInterval);
    getContentPane().add(view);
    view.requestFocusInWindow();

    // Start animation loop
    // FPSAnimator animator = new FPSAnimator(60, true);
    // animator.start();

    // Setup JFrame
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("WP Computergrafik");
    setSize(640, 480);
    setVisible(true);
  }

  /**
   * Return the root node of the scene graph.
   */
  protected RootNode getRoot() {
    return root;
  }

  /**
   * Getter.
   */
  protected Camera getCamera() {
    return view.getRenderer().getCamera();
  }

  /**
   * This callback method is called with each timer event - game loop.
   */
  protected abstract void timerTick();

  /**
   * This callback is called each time a key is pressed; can be used to handle
   * key events.
   */
  public void keyPressed(int keyCode) {
  }
}
