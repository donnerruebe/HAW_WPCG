/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Representation of GLSL shader.
 * 
 * @author Philipp Jenke
 */
public class CgGlslShader {
	/**
	 * Flag for the state of the shaders
	 */
	private boolean compiled = false;

	/**
	 * ID of the shader program.
	 */
	private int shaderProgram = -1;

	/**
	 * Filename of the vertex shader source
	 */
	private String vertexShaderFilename = "";

	/**
	 * Filename of the pixel shader source
	 */
	private String fragmentShaderFilename = "";

	public static enum ShaderType {
		VERTEX, FRAGMENT
	};

	/**
	 * Constructor.
	 */
	public CgGlslShader(String vertexShaderFilename,
			String fragmentShaderFilename) {
		this.vertexShaderFilename = vertexShaderFilename;
		this.fragmentShaderFilename = fragmentShaderFilename;
	}

	/**
	 * Getter.
	 */
	public String getVertexShaderFilename() {
		return vertexShaderFilename;
	}

	/**
	 * Setter.
	 */
	public void setVertexShaderFilename(String vertexShaderFilename) {
		this.vertexShaderFilename = vertexShaderFilename;
	}

	/**
	 * Getter.
	 */
	public String getFragmentShaderFilename() {
		return fragmentShaderFilename;
	}

	/**
	 * Setter.
	 */
	public void setFragmentShaderFilename(String pixelShaderFilename) {
		this.fragmentShaderFilename = pixelShaderFilename;
	}

	/**
	 * Setter
	 */
	public void setShaderProgram(int shaderProgram) {
		this.shaderProgram = shaderProgram;
	}

	/**
	 * Setter.
	 */
	public void setCompiled(boolean b) {
		compiled = b;
	}

	/**
	 * Getter.
	 */
	public boolean isCompiled() {
		return compiled;
	}

	/**
	 * Getter.
	 */
	public int getShaderProgram() {
		return shaderProgram;
	}

	/**
	 * Read a shader code from a source file to a String.
	 */
	public static String readShaderSource(String shaderFilename) {

		String absoluteShaderFilename = shaderFilename;

		String shaderSource = "";

		if (absoluteShaderFilename == null) {

			System.out.println("Shader source " + shaderFilename
					+ " not found - cannot read shader.");
			return shaderSource;
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(absoluteShaderFilename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
			shaderSource = sb.toString();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read shader source "
					+ absoluteShaderFilename);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to read shader source "
					+ absoluteShaderFilename);
			e.printStackTrace();
		}
		return shaderSource;
	}

	/**
	 * Get the specified source (vertex or fragment).
	 */
	public String getShaderSourceFilename(ShaderType type) {
		return (type == CgGlslShader.ShaderType.VERTEX) ? getVertexShaderFilename()
				: getFragmentShaderFilename();
	}

	/**
	 * Save the source to the specified shader filename.
	 */
	public static void saveShader(String source, String absoluteFilename) {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(absoluteFilename));
			br.write(source);
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to write shader source "
					+ absoluteFilename);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to read shader source "
					+ absoluteFilename);
			e.printStackTrace();
		}
	}
}
