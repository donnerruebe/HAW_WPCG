/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

/**
 * Parent class for all scene graph nodes.
 * 
 * @author Philipp Jenke
 *
 */
public abstract class Node
{

	private static int ID_GEN = 0;
	private final int ID = ID_GEN;
	private Vector3 color;
	private int glossiness = 20;
	private double reflexion = 0;
	public Vector3 direction;

	public Node()
	{
		ID_GEN++;
	}

	/**
	 * List of child nodes
	 */
	private List<Node> children = new ArrayList<Node>();

	/**
	 * Add a child node.
	 */
	public void addChild(Node child)
	{
		children.add(child);
	}

	public void removeChild(Node child)
	{
		children.remove(child);
	}

	/**
	 * Return the child at the given index.
	 */
	public Node getChildNode(int index)
	{
		if (index < 0 || index >= getNumberOfChildren())
		{
			System.out.println("getChildNode: invalid index.");
			return null;
		}
		return children.get(index);
	}

	/**
	 * Return the number of children
	 */
	public int getNumberOfChildren()
	{
		return children.size();
	}

	/**
	 * This method is called to draw the node using OpenGL commands. Override in
	 * implementing nodes. Do not forget to call the same method for the
	 * children.
	 */
	public abstract void drawGl(GL2 gl);

//	// DEBUGGING
//	public IntersectionResult calcIntersection(Node node, Ray3D ray)
//	{
//		return null;
//	}

	public IntersectionResult findIntersection(Node object, Ray3D ray)
	{
		return null;
	}

	/**
	 * Ermittelt zu einem Knoten alle Kinder und Kindeskinder (rekrusiv)
	 * 
	 * @return Liste aller Kinder
	 */
	public List<Node> getAllChildren()
	{
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(this);
		for (int i = 0; i < getNumberOfChildren(); i++)
		{
			nodes.addAll(getChildNode(i).getAllChildren());
		}
		return nodes;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + " :: " + ID;
	}

	/*
	 * Getter und Setter
	 */
	public Vector3 getColor()
	{
		if (color == null)
		{
			return new Vector3(0, 1, 0);
		}
		return color;
	}

	public void setColor(Vector3 color)
	{
		this.color = color;
	}

	public int getGlossiness()
	{
		return glossiness;
	}

	public void setGlossiness(int glossiness)
	{
		this.glossiness = glossiness;
	}

	public double getReflexion()
	{
		return reflexion;
	}

	public void setReflexion(double reflexion)
	{
		if (reflexion >= 0 && reflexion <= 1)
		{
			this.reflexion = reflexion;
		}
	}

}
