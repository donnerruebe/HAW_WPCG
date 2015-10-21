/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.LightSource;

/**
 * Implements the 3D rendering functionality.
 * 
 * @author Philipp Jenke
 */
public class Renderer3D {

  /**
   * Constants used to address OpenGL.
   */
  private static final double NEAR_CLIPPING_PLANE_DEFAULT = 0.1;
  private static final double FAR_CLIPPING_PLANE_DEFAULT = 10.0;
  private static final int SCREEN_WIDTH = 640;
  private static final int SCREEN_HEIGHT = 480;
  private static final float OPAQUE = 1.0f;
  private static final Vector3 CLEAR_COLOR = new Vector3(0.9, 0.9, 0.9);

  /**
   * Reference to the render frame.
   */
  private final AbstractCGFrame renderFrame;

  /**
   * Camera object.
   */
  private final Camera camera;

  /**
   * GLU instance
   */
  private GLU glu = null;

  /**
   * Set this flag to true, if the projection matrix needs an update.
   */
  private boolean updatePerspective = false;

  /**
   * Screen aspect ration
   */
  private double aspectRatio = 1;

  /**
   * Near clipping plane.
   */
  private double nearClippingPlane = NEAR_CLIPPING_PLANE_DEFAULT;

  /**
   * Far clipping plane.
   */
  private double farClippingPlane = FAR_CLIPPING_PLANE_DEFAULT;

  /**
   * Constructor.
   */
  public Renderer3D(Camera c, AbstractCGFrame renderFrame) {
    this.renderFrame = renderFrame;
    camera = c;
    glu = new GLU();
  }

  /**
   * This method is called once when the OpenGL context is created.
   * 
   * @param gl
   *          OpenGl object.
   */
  public void onSurfaceCreated(GL2 gl) {
    gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
    gl.glLoadIdentity();

    // Perspective
    final int width = SCREEN_WIDTH;
    final int height = SCREEN_HEIGHT;
    aspectRatio = (float) width / (float) height;
    glu.gluPerspective(camera.getOpeningAngle(), aspectRatio, nearClippingPlane, farClippingPlane);

    // Viewport
    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

    // Depth
    gl.glEnable(GL2.GL_DEPTH_TEST);

    // which is the front? the one which is drawn counter clockwise
    gl.glFrontFace(GL2.GL_CCW);
    // which one should NOT be drawn
    gl.glCullFace(GL2.GL_BACK);

    // define the color we want to be displayed as the "clipping wall"
    gl.glClearColor((float) (CLEAR_COLOR.get(0)), (float) (CLEAR_COLOR.get(1)), (float) (CLEAR_COLOR.get(2)), OPAQUE);

    // Flat shading
    gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

    // Set a light, just use the first one from the root node.
    if (renderFrame != null && renderFrame.getRoot().getNumberOfLightSources() > 0) {
      LightSource lightSource = renderFrame.getRoot().getLightSource(0);
      float lightAmbient[] = { 1, 1, 1, 1 };
      float lightSpecular[] = { 1, 1, 1, 1 };
      float lightPosition[] = lightSource.getPosition().floatData();
      float lightDiffuse[] = lightSource.getColor().floatData();
      gl.glEnable(GL2.GL_LIGHT0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightAmbient, 0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightSpecular, 0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightDiffuse, 0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
    }

    updateView(gl);
  }

  /**
   * Event-Handler: surface changed. (non-Javadoc)
   */
  public void onSurfaceChanged(GL2 gl, int w, int h) {
    gl.glViewport(0, 0, w, h);
    gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
    gl.glLoadIdentity();
    aspectRatio = (float) w / (float) h;
    glu.gluPerspective(camera.getOpeningAngle(), aspectRatio, nearClippingPlane, farClippingPlane);
    gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

    updateView(gl);
  }

  public void onDrawFrame(GL2 gl) {
    // clear the color buffer and the depth buffer
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

    if (updatePerspective) {
      if (gl != null) {
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(camera.getOpeningAngle(), aspectRatio, nearClippingPlane, farClippingPlane);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
      }
      updatePerspective = false;
    }

    updateView(gl);

    // Flat shading
    gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

    if (renderFrame != null) {
      renderFrame.getRoot().drawGl(gl);
    }
  }

  /**
   * Update the GL matrix stack based on the camera.
   */
  private void updateView(GL2 gl) {
    gl.glLoadIdentity();
    final float eyeX = (float) camera.getEye().get(0);
    final float eyeY = (float) camera.getEye().get(1);
    final float eyeZ = (float) camera.getEye().get(2);
    final float refX = (float) camera.getRef().get(0);
    final float refY = (float) camera.getRef().get(1);
    final float refZ = (float) camera.getRef().get(2);
    final float upX = (float) camera.getUp().get(0);
    final float upY = (float) camera.getUp().get(1);
    final float upZ = (float) camera.getUp().get(2);
    glu.gluLookAt(eyeX, eyeY, eyeZ, refX, refY, refZ, upX, upY, upZ);
  }

  /**
   * Getter.
   */
  public Camera getCamera() {
    return camera;
  }

  public void setClipplingPlanes(double near, double far) {
    nearClippingPlane = near;
    farClippingPlane = far;
    updatePerspective = true;
  }

  public AbstractCGFrame getRenderFrame() {
    return renderFrame;
  }
}
