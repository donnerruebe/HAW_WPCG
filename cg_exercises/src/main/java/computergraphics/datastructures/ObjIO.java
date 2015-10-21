/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.datastructures;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import computergraphics.math.Vector3;

/**
 * (Vereinfachtes) Lesen eines Dreiecksnetzes aus einer Wavefront oBJ Datei.
 * 
 * @author Philipp Jenke
 */
public class ObjIO {

  /**
   * Name des aktuellen Unterverzeichnises (für relative Pfade benötigt).
   */
  private String directory = "";

  /**
   * Konstructor.
   */
  public ObjIO() {
  }

  /**
   * Lesen eines Dreiecksnetzes aus einer OBJ-Datei. Die Information wird in das
   * Dreiecksnetz 'mesh' geschrieben.
   */
  public void einlesen(final String filename, ITriangleMesh mesh) {

    System.out.println("Einlesen der OBJ-Datei " + filename);

    // Setup
    if (mesh == null) {
      System.out.println("Ungültiges Dreiecksnetz - Abbruch.");
      return;
    }
    mesh.clear();
    directory = new File(filename).getParent() + "/";

    String strLine = "";
    try {
      InputStream is = new FileInputStream(filename);
      DataInputStream in = new DataInputStream(is);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      while ((strLine = br.readLine()) != null) {
        parseLine(strLine, directory, mesh);
      }
      in.close();
    } catch (Exception e) {
      System.out.println("Fehler beim Lesen aus der OBJ-Datei.");
    }

    System.out.println("OBJ-Datei " + filename + " mit " + mesh.getNumberOfVertices() + " Vertices und "
        + mesh.getNumberOfTriangles() + " Dreiecken erfolgreich eingelesen.");

  }

  /**
   * Einlesen einer Zeile aus der OBJ-Datei.
   */
  private void parseLine(String strLine, final String directory, ITriangleMesh mesh) {
    String line = trim(strLine);
    String operator = getOperator(line);
    if (operator.equals("v")) {
      // Lesen eines Vertex
      Vertex v = parseVertex(line);
      if (v != null) {
        // System.out.println("Vertex: " + v);
        mesh.addVertex(v);
      }
    } else if (operator.equals("f")) {
      // Lesen einer Facette (Dreieck)
      int[] t = parseFace(line);
      if (t != null) {
        // System.out.println("Dreieck: " + t);
        mesh.addTriangle(t[0], t[1], t[2]);
      }
    } else if (operator.equals("vt")) {
      // Lesen einer Texturkoordinate
      Vector3 t = parseTextureCoordinate(line);
      if (t != null) {
        // System.out.println("TexCoord: " + t);
        // mesh.addTextureCoordinate(t);
      }
    } else if (operator.equals("mtllib")) {
      // Lesen der Materialdatei (Texturname)
      String textureFilename = parseUseMaterial(line);
      if (textureFilename != null) {
        // System.out.println("Textur: " + textureFilename);
        mesh.setTextureFilename(textureFilename);
      }
    }
  }

  /**
   * Einlesen einer Materialdatei mit Texturinformtion. Liefert den Namen der
   * Texturdatei. Liefert null, falls keine Textur gefunden wurde.
   */
  private String parseUseMaterial(String line) {
    String[] components = line.split("\\s+");
    if (components.length == 2) {
      String materialFilename = components[1];
      String textureFilename = readTextureFilenameFromMaterialFile(directory + materialFilename);
      if (textureFilename.length() > 0) {
        return directory + textureFilename;
      }
    }
    return null;
  }

  /**
   * Offnen einer Materialdatei und Suchen nach Texturinformation.
   */
  private String readTextureFilenameFromMaterialFile(String materialFilename) {
    InputStream is;
    String textureFilename = null;
    try {
      is = new FileInputStream(materialFilename);
      DataInputStream in = new DataInputStream(is);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;
      while ((strLine = br.readLine()) != null) {
        String line = strLine.trim();
        String operator = getOperator(line);
        if (operator.compareTo("map_Kd") == 0) {
          String[] texFilenameCommand = line.split(" ");
          if (texFilenameCommand.length == 2) {
            textureFilename = texFilenameCommand[1];
          }
        }
      }
      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("Fehler beim Lesen aus Materialdatei " + materialFilename);
      return null;
    } catch (IOException e) {
      System.out.println("Fehler beim Lesen aus Materialdatei " + materialFilename);
      return null;
    }

    return textureFilename;
  }

  /**
   * Entfernen doppelter Leerzeichen aus einer Zeile.
   */
  private static String trim(String strLine) {
    String line = strLine.trim();
    line = line.replaceAll("  ", " ");
    int l = line.length();
    while (l < line.length()) {
      l = line.length();
      line = line.replaceAll("  ", " ");
    }
    return line;
  }

  /**
   * Auslesen einer Zeile, die eine Texturkoordinate beinhaltet.
   */
  private Vector3 parseTextureCoordinate(String strLine) {
    String line = trim(strLine);
    String[] allCoords = line.split("\\s+");
    float u = 0;
    float v = 0;
    if (allCoords.length >= 3) {
      u = getFloatValue(allCoords[1]);
      v = getFloatValue(allCoords[2]);
      return new Vector3(u, v, 0);
    }

    return null;
  }

  /**
   * String -> float.
   */
  private float getFloatValue(String string) {
    if (string.length() == 0) {
      return 0;
    }
    return Float.valueOf(string);
  }

  /**
   * Lesen einer Zeile, die ein Dreieck repräsentiert (Indices der Eckpunkte,
   * Indices der Texturkoordinaten).
   */
  private int[] parseFace(String strLine) {
    String[] allCoords = strLine.split("\\s+");

    int[] vertexIndices = { -1, -1, -1 };
    int[] texCoordIndices = { -1, -1, -1 };
    int[] triangle = { -1, -1, -1, -1, -1, -1 };

    for (int i = 0; i < 3; i++) {
      String coordinateString = allCoords[i + 1];
      String[] tokens = coordinateString.split("/");
      if (tokens.length > 0) {
        vertexIndices[i] = Integer.parseInt(tokens[0]) - 1;
      }
      if (tokens.length > 1) {
        texCoordIndices[i] = Integer.parseInt(tokens[1]) - 1;
      }
    }

    if (vertexIndices[0] >= 0 && texCoordIndices[0] >= 0) {
      triangle[0] = vertexIndices[0];
      triangle[1] = vertexIndices[1];
      triangle[2] = vertexIndices[2];
      triangle[3] = texCoordIndices[0];
      triangle[4] = texCoordIndices[1];
      triangle[5] = texCoordIndices[2];
      return triangle;
    } else if (vertexIndices[0] >= 0) {
      triangle[0] = vertexIndices[0];
      triangle[1] = vertexIndices[1];
      triangle[2] = vertexIndices[2];
      return triangle;
    }

    return null;
  }

  /**
   * Lesen einer Zeile, die einen Vertex repräsentiert.
   */
  private Vertex parseVertex(String strLine) {
    String[] components = strLine.split("\\s+");

    if (components.length == 4) {
      float x = Float.parseFloat(components[1]);
      float y = Float.parseFloat(components[2]);
      float z = Float.parseFloat(components[3]);
      return new Vertex(new Vector3(x, y, z), new Vector3(1, 0, 0), new Vector3(0.25, 0.75, 0.25));
    }
    return null;
  }

  /**
   * Extract the operator char from a line.
   * 
   * @param strLine
   * @return String representing the operator
   */
  private String getOperator(String strLine) {
    String[] components = strLine.split("\\s+");
    if (components.length > 0) {
      return components[0];
    } else {
      return "";
    }
  }
}
