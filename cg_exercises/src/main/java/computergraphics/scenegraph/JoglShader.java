/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL2;

/**
 * Shader representation for the JOGL framework.
 * 
 * @author Philipp Jenke
 */
public class JoglShader {

  /**
   * Shader constants.
   */
  private static final int COMPILE_STATUS_OK = 1;
  private static final int LINK_STATUS_OK = 0;

  /**
   * Convert to GL shader constants.
   */
  public static int getGlShaderType(
      CgGlslShader.ShaderType type) {
    if (type == CgGlslShader.ShaderType.VERTEX) {
      return GL2.GL_VERTEX_SHADER;
    } else if (type == CgGlslShader.ShaderType.FRAGMENT) {
      return GL2.GL_FRAGMENT_SHADER;
    } else {
      return -1;
    }
  }

  /**
   * Compile and link the shaders.
   */
  private static void compileAndLink(CgGlslShader shader,
      GL2 gl) {

    // We set this flag to true even if the shader is not working. Otherwise
    // the tries again over and over.
    shader.setCompiled(true);

    // Compile
    int v =
        compileShader(
            gl,
            getGlShaderType(CgGlslShader.ShaderType.VERTEX),
            shader.getVertexShaderFilename());
    int f =
        compileShader(
            gl,
            getGlShaderType(CgGlslShader.ShaderType.FRAGMENT),
            shader.getFragmentShaderFilename());
    if (v < 0 || f < 0) {
      System.out.println("Shader not created.");
      return;
    }

    // Link
    int shaderProgram = linkProgram(gl, v, f);
    if (shaderProgram < 0) {
      System.out.println("Shader not created.");
      return;
    }

    shader.setShaderProgram(shaderProgram);
    System.out
        .println("Successfully created shader with\n  "
            + shader.getVertexShaderFilename() + "\n  "
            + shader.getFragmentShaderFilename());
  }

  /**
   * Link the vertex and fragment shaders.
   */
  private static int linkProgram(GL2 gl,
      int vertexShaderId, int fragmentShaderId) {
    int shaderProgram = gl.glCreateProgram();
    gl.glAttachShader(shaderProgram, vertexShaderId);
    gl.glAttachShader(shaderProgram, fragmentShaderId);
    gl.glLinkProgram(shaderProgram);
    gl.glValidateProgram(shaderProgram);
    IntBuffer intBuffer = IntBuffer.allocate(1);
    gl.glGetProgramiv(shaderProgram, GL2.GL_LINK_STATUS,
        intBuffer);
    if (checkLinkError(shaderProgram, gl)) {
      String errorMsg =
          getLinkErrorMessage(shaderProgram, gl);
      System.out.println("Link error " + ": \n" + errorMsg);
    }
    return shaderProgram;
  }

  /**
   * Compile the specified shader from the filename and return the OpenGL id.
   */
  public static int compileShader(GL2 gl, int shaderType,
      String shaderFilename) {
    String vsrc =
        CgGlslShader.readShaderSource(shaderFilename);
    int id = compileShaderFromSource(gl, shaderType, vsrc);
    if (id < 0) {
      System.out.println("Compile error in shader file "
          + shaderFilename);
    }
    return id;
  }

  /**
   * Compile the specified shader from the filename and return the OpenGL id.
   */
  public static int compileShaderFromSource(GL2 gl,
      int shaderType, String shaderSource) {
    int id = gl.glCreateShader(shaderType);
    gl.glShaderSource(id, 1, new String[] { shaderSource },
        (int[]) null, 0);
    gl.glCompileShader(id);
    if (checkCompileError(id, gl)) {
      String errorMsg = getCompileErrorMessage(id, gl);
      System.out.println(errorMsg);
      return -1;
    }
    return id;
  }

  /**
   * Delete the shader with the given id.
   */
  public static void deleteShader(GL2 gl, int id) {
    if (id < 0) {
      System.out.println("deleteShader: invalid id");
      return;
    }
    gl.glDeleteShader(id);
  }

  /**
   * Check if a linker error has occurred.
   */
  private static boolean checkLinkError(int id, GL2 gl) {
    IntBuffer intBuffer = IntBuffer.allocate(1);
    gl.glGetShaderiv(id, GL2.GL_LINK_STATUS, intBuffer);
    return intBuffer.get(0) != LINK_STATUS_OK;
  }

  /**
   * Extract the error message.
   */
  private static String getCompileErrorMessage(int id,
      GL2 gl) {
    IntBuffer intBuffer = IntBuffer.allocate(1);
    gl.glGetShaderiv(id, GL2.GL_INFO_LOG_LENGTH, intBuffer);
    int size = intBuffer.get(0);
    String errorMessage = "";
    if (size > 0) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(size);
      gl.glGetShaderInfoLog(id, size, intBuffer, byteBuffer);
      for (byte b : byteBuffer.array()) {
        errorMessage += (char) b;
      }
    }
    return errorMessage;
  }

  /**
   * Extract the error message.
   */
  private static String getLinkErrorMessage(int id, GL2 gl) {
    IntBuffer intBuffer = IntBuffer.allocate(1);
    gl.glGetProgramiv(id, GL2.GL_INFO_LOG_LENGTH, intBuffer);
    int size = intBuffer.get(0);
    String errorMessage = "";
    if (size > 0) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(size);
      gl.glGetProgramInfoLog(id, size, intBuffer,
          byteBuffer);
      for (byte b : byteBuffer.array()) {
        errorMessage += (char) b;
      }
    }
    return errorMessage;
  }

  /**
   * Check if a compile error (vertex or fragment shader) occurred?
   */
  private static boolean checkCompileError(int id, GL2 gl) {
    IntBuffer intBuffer = IntBuffer.allocate(1);
    gl.glGetShaderiv(id, GL2.GL_COMPILE_STATUS, intBuffer);
    return intBuffer.get(0) != COMPILE_STATUS_OK;
  }

  /**
   * Activate the shader
   */
  public static void use(CgGlslShader shader, GL2 gl) {
    if (!shader.isCompiled()) {
      compileAndLink(shader, gl);
    }
    gl.glUseProgram(shader.getShaderProgram());
  }
}
