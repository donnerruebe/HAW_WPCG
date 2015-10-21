/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import computergraphics.math.Vector3;

/**
 * This class represents a view for 3D content.
 * 
 * @author Philipp Jenke
 */
public class View3D extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  /**
     * 
     */
  private static final long serialVersionUID = 1L;

  /**
   * Size of the GL widget - width.
   */
  private static final int WIDGET_WIDTH = 640;

  /**
   * Size of the GL widget - height.
   */
  private static final int WIDGET_HEIGHT = 480;

  /**
   * Renderer object
   */
  private final Renderer3D renderer3d;

  /**
   * Last coordinates of the mouse
   */
  private Vector3 lastMouseCoordinates = new Vector3(-1, -1, 0);

  /**
   * Camera object.
   */
  private Camera camera = new Camera();

  /**
   * Abstraction of the camera control
   */
  private CameraController cameraController = new CameraController(camera);

  /**
   * Remember the current button.
   */
  private int currentButton = -1;

  /**
   * Constructor
   */
  public View3D(AbstractCGFrame renderFrame) {
    super(new GLCapabilities(GLProfile.getDefault()));

    renderer3d = new Renderer3D(camera, renderFrame);

    addGLEventListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
    addKeyListener(this);

    setSize(WIDGET_WIDTH, WIDGET_HEIGHT);

    // Start the Gl loop.
    FPSAnimator animator = new FPSAnimator(this, 60, true);
    animator.start();
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    renderer3d.onDrawFrame(drawable.getGL().getGL2());
  }

  @Override
  public void dispose(GLAutoDrawable arg0) {
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    renderer3d.onSurfaceCreated(drawable.getGL().getGL2());

  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    renderer3d.onSurfaceChanged(drawable.getGL().getGL2(), w, h);
  }

  @Override
  public void mouseClicked(MouseEvent event) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent event) {
    currentButton = event.getButton();
    if (event.getButton() == MouseEvent.BUTTON1) {
      lastMouseCoordinates.set(0, event.getX());
      lastMouseCoordinates.set(1, event.getY());
    }
  }

  @Override
  public void mouseReleased(MouseEvent event) {
    lastMouseCoordinates = new Vector3(event.getX(), event.getY(), 0);
    currentButton = -1;
  }

  @Override
  public void mouseDragged(MouseEvent event) {
    if (currentButton == MouseEvent.BUTTON1) {
      if ((lastMouseCoordinates.get(0) > 0) && (lastMouseCoordinates.get(1) > 0)) {
        cameraController.mouseDeltaXLeftButton((float) (event.getX() - lastMouseCoordinates.get(0)));
        cameraController.mouseDeltaYLeftButton((float) (event.getY() - lastMouseCoordinates.get(1)));
      }
      lastMouseCoordinates.set(0, event.getX());
      lastMouseCoordinates.set(1, event.getY());
    } else if (currentButton == MouseEvent.BUTTON3) {
      if ((lastMouseCoordinates.get(0) > 0) && (lastMouseCoordinates.get(1) > 0)) {
        cameraController.mouseDeltaXRightButton((float) (event.getX() - lastMouseCoordinates.get(0)));
        cameraController.mouseDeltaYRightButton((float) (event.getY() - lastMouseCoordinates.get(1)));
      }
      lastMouseCoordinates.set(0, event.getX());
      lastMouseCoordinates.set(1, event.getY());
    }
  }

  @Override
  public void mouseMoved(MouseEvent event) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyChar() == '+') {
      cameraController.mouseDeltaYRightButton(-1);
    } else if (e.getKeyChar() == '-') {
      cameraController.mouseDeltaYRightButton(1);
    } else {
      renderer3d.getRenderFrame().keyPressed(e.getKeyCode());
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  /**
   * Getter.
   */
  public Renderer3D getRenderer() {
    return renderer3d;
  }
}