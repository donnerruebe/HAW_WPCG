package computergraphics.applications.blatt2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

public class HalfEdgeTriangleMesh implements ITriangleMesh
{
	private List<Vertex> vertices = new ArrayList<Vertex>();
	private List<HalfEdge> halfEdges = new ArrayList<HalfEdge>();
	private List<TriangleFacet> facets = new ArrayList<TriangleFacet>();

	public List<Vertex> getVertices()
	{
		return vertices;
	}

	public List<HalfEdge> getHalfEdges()
	{
		return halfEdges;
	}

	public List<TriangleFacet> getFacets()
	{
		return facets;
	}

	public HalfEdgeTriangleMesh()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3)
	{
		//System.out.println(vertexIndex1 + "::" + vertexIndex2 + "::" + vertexIndex3);
		// Facette erzeugen
		TriangleFacet facet = new TriangleFacet();

		Vertex v1 = vertices.get(vertexIndex1);
		Vertex v2 = vertices.get(vertexIndex2);
		Vertex v3 = vertices.get(vertexIndex3);

		// HalfEdge 1 erzeugen und mit Vertext 1 verknüpfen
//		HalfEdge he12 = new HalfEdge();
//		he12.setFacet(facet);
//		he12.setStartVertex(v1);
//		v1.setHalfEgde(he12);
		
		HalfEdge he1 = new HalfEdge();
		he1.setFacet(facet);
		he1.setStartVertex(v1);
		v1.setHalfEgde(he1);

		// HalfEdge 2 erzeugen und mit Vertext 2 verknüpfen
		HalfEdge he2 = new HalfEdge();
		he2.setFacet(facet);
		he2.setStartVertex(v2);
		v2.setHalfEgde(he2);
		
		// HalfEdge 3 erzeugen und mit Vertext 3 verknüpfen
		HalfEdge he3 = new HalfEdge();
		he3.setFacet(facet);
		he3.setStartVertex(v3);
		v3.setHalfEgde(he3);
		
		// StartHalfEdge für die Facette setzen
		facet.setHalfEdge(he1);
		
		// Setzen der Folgenden HalfEdges
		he1.setNext(he2);
		he2.setNext(he3);
		he3.setNext(he1);
		
		// Setzen der gegenüberliegenden HalfEdge
//		setOppositeHalfEdge(he1);
//		setOppositeHalfEdge(he2);
//		setOppositeHalfEdge(he3);
		
		// Hinzufügen der Kanten und der Facette zu den Listen
		halfEdges.add(he1);
		halfEdges.add(he2);
		halfEdges.add(he3);
		facets.add(facet);
	}
	
	/**
	 * Diese Methode berechnet zu jeder Halbkante die gegenüberliegende, indem das komplette Netz einmal durchlaufen wird
	 */
	public void setOppositeHalfEdges()
	{
		// Schleife für jede Halbkante
		for (HalfEdge h1: getHalfEdges())
		{
			// Prüfen, ob die gegenüberliegende Halbkante bereits gesetzt ist (dann muss nichts unternommen werden)
			if (h1.getOpposite() == null)
			{
				// Durchlaufe alle Halbkanten
				for (HalfEdge h2 : getHalfEdges())
				{
					// Prüfe, ob diese Halbkante den gleichen Startknoten, wie der Endknoten der eigentlichen Halbkante hat
					if (!h1.equals(h2))
					{
						if (
								// Beide Kanten verlaufen entgegengesetzt (unterschiedliche Start- und Zielknoten
								(h1.getNext().getStartVertex().equals(h2.getStartVertex()) && h2.getNext().getStartVertex().equals(h1.getStartVertex())) ||
								// Beide Kanten verlaufen in eine Richtung (also gleicher Start- und Endknoten)
								(h1.getStartVertex().equals(h2.getStartVertex()) && h1.getNext().getStartVertex().equals(h2.getNext().getStartVertex()))
							)
						{
							h1.setOpposite(h2);
							h2.setOpposite(h1);
							break;
						}
					}
					
				}
			}
		}
	}

	@Override
	public int addVertex(Vertex v)
	{
		if (vertices.add(v))
		{
			return vertices.size() -1;
		}
		return -1;
	}

	@Override
	public int getNumberOfTriangles()
	{
		return facets.size();
	}

	@Override
	public int getNumberOfVertices()
	{
		return vertices.size();
	}

	@Override
	public Vertex getVertex(int index)
	{
		return vertices.get(index);
	}

	@Override
	public TriangleFacet getFacet(int facetIndex)
	{
		return facets.get(facetIndex);
	}

	@Override
	public void clear()
	{
		facets.clear();
		halfEdges.clear();
		vertices.clear();
	}

	@Override
	public void computeTriangleNormals()
	{
		for (TriangleFacet f:facets)
		{
			Vector3 v11 = f.getHalfEdge().getStartVertex().getPosition();
			Vector3 v22 = f.getHalfEdge().getNext().getStartVertex().getPosition();
			Vector3 v33 = f.getHalfEdge().getNext().getNext().getStartVertex().getPosition();

			// Kreuzprodukte berechnen
			Vector3 sv11 = v11.cross(v22);
			Vector3 sv22 = v22.cross(v33);
			Vector3 sv33 = v33.cross(v11);
			
			// Vectoren aufsummieren und normieren. Normale zuweisen
			f.setNormal(sv11.add(sv22).add(sv33).getNormalized());
		}
		
	}
	
	public void computeVertexNormals()
	{
		for (Vertex v: vertices)
		{
			// Start-Facette anhand der ersten Halbkante
			HalfEdge start_edge = v.getHalfEdge();
			
			// Normale der ersten Facette
			Vector3 normal = start_edge.getFacet().getNormal();
			
			HalfEdge current_edge = start_edge.getOpposite().getNext();
			
			// Durchlaufe alle anliegenden Facetten ueber die Halbkanten
			while (current_edge.getOpposite().getNext() != start_edge)
			{
				// Normale aufaddieren
				normal.add(current_edge.getFacet().getNormal());
				// Gehe zur naechsten Facette (ueber deren Halbkante)
				current_edge = current_edge.getOpposite().getNext();
			}
			v.setNormal(normal.getNormalized());
		}
		
	}
	
	public void laplace()
	{
		double alpha = 0.5;
		List<Vector3> schwerpunkte = new ArrayList<Vector3>();
		for (int i = 0; i < vertices.size(); i ++)
		{
			Vertex v = vertices.get(i);
			// Start-Facette anhand der ersten Halbkante
			HalfEdge start_edge = v.getHalfEdge().getOpposite();
			
			int anzahl = 1;
			// Summe aller Positionen der anliegenden Vertizes
			Vector3 sum = start_edge.getStartVertex().getPosition();
			
			HalfEdge current_edge = start_edge.getNext().getOpposite();
			
			// Durchlaufe alle anliegenden Facetten ueber die Halbkanten
			while (current_edge != start_edge)
			{
				// Positionen aufaddieren
				sum = sum.add(current_edge.getStartVertex().getPosition());
				// Gehe zur naechsten Facette (ueber deren Halbkante)
				current_edge = current_edge.getNext().getOpposite();
				anzahl ++;
			}
			// Zwischenspeichern der berechnteten Schwerpunkte
			schwerpunkte.add(sum.multiply(1./anzahl));
		}
		
		for (int i = 0; i < vertices.size(); i ++)
		{
			
			// Erzeuge neuen Vertext
			Vertex x = new Vertex(schwerpunkte.get(i).multiply(1.-alpha).add(vertices.get(i).getPosition().multiply(alpha)));
			x.setHalfEgde(vertices.get(i).getHalfEdge());
			
			
			// Start-Facette anhand der ersten Halbkante
			HalfEdge start_edge = x.getHalfEdge();
			start_edge.setStartVertex(x);
		
			HalfEdge current_edge = start_edge.getOpposite().getNext();
			
			// Durchlaufe alle anliegenden Facetten ueber die Halbkanten
			while (current_edge != start_edge)
			{
				// Positionen aufaddieren
				current_edge.setStartVertex(x);
				// Gehe zur naechsten Facette (ueber deren Halbkante)
				current_edge = current_edge.getOpposite().getNext();
				
			}
						
			vertices.set(i, x);
		}
		computeTriangleNormals();
		computeVertexNormals();
		
	}
	
	public void calculateVertexKruemmungColor()
	{

		computeTriangleNormals();
		computeVertexNormals();
		
		double k_min = Double.MAX_VALUE;
		double k_max = Double.MIN_VALUE;
		List<Double> k_values = new ArrayList<Double>();
		
		for (Vertex v: vertices)
		{
			HalfEdge start_edge = v.getHalfEdge().getOpposite();
			
			int anzahl = 0;
			double sum = 0;
			double sum_a = 0;
			
			
			HalfEdge current_edge = start_edge.getNext().getOpposite();
			
			// Durchlaufe alle anliegenden Facetten ueber die Halbkanten
			while (current_edge != start_edge)
			{
				// Positionen aufaddieren
				//sum += Math.acos((v.getPosition().multiply(current_edge.getStartVertex().getPosition())) / (v.getPosition().getNorm() * current_edge.getStartVertex().getPosition().getNorm())); 
				//sum += Math.acos((v.getPosition().multiply(current_edge.getStartVertex().getPosition())) / (v.getPosition().getNorm() * current_edge.getStartVertex().getPosition().getNorm()));
				sum += Math.acos((v.getNormal().multiply(current_edge.getFacet().getNormal()))); /// (v.getNormal().getNorm() * current_edge.getStartVertex().getPosition().getNorm()));
				sum_a += current_edge.getFacet().getArea();
				// Gehe zur naechsten Facette (ueber deren Halbkante)
				current_edge = current_edge.getNext().getOpposite();
				anzahl ++;
			}
			
			double gamma = sum / anzahl;
			double k = gamma / 1;//sum_a;
			
			k_values.add(k);
			if (k < k_min)
			{
				k_min = k;
			}
			if (k > k_max)
			{
				k_max = k;
			}
			
		}
		System.out.println(k_min + "::" + k_max);
		Vector3 gruen = new Vector3(0, 1, 0);
		//Vector3 gelb = new Vector3(1, 1, 0);
		
		for (int i = 0; i < vertices.size(); i ++)
		{
			//vertices.get(i).setColor(gelb.subtract(gruen.multiply(((k_values.get(i)-k_min)/(k_max-k_min)))));
			vertices.get(i).setColor(gruen.multiply(((k_values.get(i)-k_min)/(k_max-k_min))));
		}
		
	}

	@Override
	public void setTextureFilename(String filename)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTextureFilename()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void printMeshData()
	{
		System.out.println("Printing Mesh Data");
	    for (TriangleFacet f: getFacets())
	    {
	    	System.out.println("Facet");
	    	HalfEdge e = f.getHalfEdge();
	    	System.out.println("- " + e.getStartVertex());
	    	e = e.getNext();
	    	System.out.println("- " + e.getStartVertex());
	    	e = e.getNext();
	    	System.out.println("- " + e.getStartVertex());
	    }
	}
}
