package computergraphics.applications.blatt4;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.applications.blatt2.HalfEdgeTriangleMesh;
import computergraphics.applications.blatt2.ImplicitWirr;
import computergraphics.applications.blatt2.ImplicitTorus;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;

public class MarchingCubes
{
	public static final double GRID_STEP = 0.05;
	public static final double GRID_SIZE = 2;
	private static final double ISO = 0;
	
	public HalfEdgeTriangleMesh mesh = new HalfEdgeTriangleMesh();;
	
	public MarchingCubes(ImplicitFunction func)
	{
		//ImplicitFunction func = new ImplicitCube(0);//ImplicitTorus(0.2, 1);//new ImplicitSphere(1);
		//List<Vector3> points = new ArrayList<Vector3>();
		//List<Double> values = new ArrayList<Double>();
		for (double x = -GRID_SIZE; x < GRID_SIZE; x += GRID_STEP)
		{
			for (double y = -GRID_SIZE; y < GRID_SIZE; y += GRID_STEP)
			{
				for (double z = -GRID_SIZE; z < GRID_SIZE; z += GRID_STEP)
				{
					List<Vector3> points = createCubeVertices(new Vector3(x,y,z), GRID_STEP);
					List<Double> values = createIsoValues(points, func);
					//values.add(sphere.getIso(p));
					createTriangles(points, values);
					
				}
			}
		}
		
		
	}
	
	private List<Vector3> createCubeVertices(Vector3 startVertex, double edgeLength)
	{
		List<Vector3> list = new ArrayList<Vector3>();
		list.add(startVertex);
		list.add(startVertex.add(new Vector3(edgeLength,0,0)));
		list.add(startVertex.add(new Vector3(edgeLength,edgeLength,0)));
		list.add(startVertex.add(new Vector3(0,edgeLength,0)));
		list.add(startVertex.add(new Vector3(0,0,edgeLength)));
		list.add(startVertex.add(new Vector3(edgeLength,0,edgeLength)));
		list.add(startVertex.add(new Vector3(edgeLength,edgeLength,edgeLength)));
		list.add(startVertex.add(new Vector3(0,edgeLength,edgeLength)));
		return list;
	}
	
	private List<Double> createIsoValues(List<Vector3> points, ImplicitFunction function)
	{
		List<Double> values = new ArrayList<Double>();
		for (Vector3 p:points){
			values.add(function.getIso(p));
		}
		return values;
	}
	
	
	private void createTriangles( List<Vector3> points, List<Double> values )
	{
		int value = 0;
		value += values.get(0)>ISO?1:0;
		value += values.get(1)>ISO?2:0;
		value += values.get(2)>ISO?4:0;
		value += values.get(3)>ISO?8:0;
		value += values.get(4)>ISO?16:0;
		value += values.get(5)>ISO?32:0;
		value += values.get(6)>ISO?64:0;
		value += values.get(7)>ISO?128:0;
		int[] kanten = new int[15];
		System.arraycopy(Faces3D.faces, value * 15, kanten, 0, 15);
		//System.out.println(value);
		for (int i = 0; i < 15; i += 3)
		{
			//int[] kanten = {Faces3D.faces[i+value],Faces3D.faces[i+value+1],Faces3D.faces[i+value+2]};
			if (kanten[i] != -1)
			{
				double weight = 0.5;
				Vector3 p1 = points.get(EdgeToVertex.edges[kanten[i]][0]);
				Vector3 p2 = points.get(EdgeToVertex.edges[kanten[i]][1]);
				
				double valA = values.get(EdgeToVertex.edges[kanten[i]][0]);
				double valB = values.get(EdgeToVertex.edges[kanten[i]][1]);
				weight = (ISO - valA) / (valB- valA);
				
				Vector3 v1 = p1.multiply(1-weight).add(p2.multiply(weight));
				mesh.addVertex(new Vertex(v1));

				
				
				p1 = points.get(EdgeToVertex.edges[kanten[i+1]][0]);
				p2 = points.get(EdgeToVertex.edges[kanten[i+1]][1]);
				
				valA = values.get(EdgeToVertex.edges[kanten[i+1]][0]);
				valB = values.get(EdgeToVertex.edges[kanten[i+1]][1]);
				weight = (ISO - valA) / (valB- valA);

				Vector3 v2 = p1.multiply(1-weight).add(p2.multiply(weight));
				mesh.addVertex(new Vertex(v2));

				
				p1 = points.get(EdgeToVertex.edges[kanten[i+2]][0]);
				p2 = points.get(EdgeToVertex.edges[kanten[i+2]][1]);
				
				valA = values.get(EdgeToVertex.edges[kanten[i+2]][0]);
				valB = values.get(EdgeToVertex.edges[kanten[i+2]][1]);
				weight = (ISO - valA) / (valB- valA);
				
				Vector3 v3 = p1.multiply(1-weight).add(p2.multiply(weight));
				mesh.addVertex(new Vertex(v3));
				//System.out.println("Triangle" + i);
				mesh.addTriangle(mesh.getNumberOfVertices()-3, mesh.getNumberOfVertices()-2, mesh.getNumberOfVertices()-1);
			}
		}
		
		
		// Durchlaufe alle Punkte und bilde Cluster (der letzte wird nicht durch die Schleife durchlaufen)
//		for (int x = 0; x < GRID_SIZE - 1; x ++)
//		{
//			for (int y = 0; y < GRID_SIZE - 1; y ++)
//			{
//				for (int z = 0; z < GRID_SIZE - 1; z ++)
//				{
//					int value = 0;
//					// Achtung, Reihenfolge der Punkte wichtig (?!)
//					int p0 = transform(x, y, z);
//					int p1 = transform(x+1, y, z);
//					int p2 = transform(x+1, y+1, z);
//					int p3 = transform(x, y+1, z);
//					int p4 = transform(x, y, z+1);
//					int p5 = transform(x+1, y, z+1);
//					int p6 = transform(x+1, y+1, z+1);
//					int p7 = transform(x, y+1, z+1);
//					//mesh.addVertex(new Vertex(new Vector3(x, y, z)));
//					
//					// Berechne Index
//					value += values.get(p0)>=ISO?1:0;
//					value += values.get(p1)>=ISO?2:0;
//					value += values.get(p2)>=ISO?4:0;
//					value += values.get(p3)>=ISO?8:0;
//					value += values.get(p4)>=ISO?16:0;
//					value += values.get(p5)>=ISO?32:0;
//					value += values.get(p6)>=ISO?64:0;
//					value += values.get(p7)>=ISO?128:0;
//					System.out.println("---------------------------");
//					// Durchlaufe alle Indicies pro Wuerfel (pruefe also alle moeglichen Dreiecke
//					for (int i = 0; i <= 12; i += 3)
//					{
//						if (Faces3D.faces[i+value] != -1)
//						{
//							System.out.println(i+value);
//							System.out.println(Faces3D.faces[i+value]);
//						}
//					}
//				}
//			}
//		}
		
		//System.out.println(Faces3D.faces[value]);	
	}
	
	/*
	 * Transformiert eine Position von XYZ eins eindimensionale
	 * 
	 */
//	private int transform(int x, int y, int z)
//	{
//		return x + STEPS * (y + STEPS * z);
//	}
	
//	private Vector3 interpolate(Vector3 a, Vector3 b, double va, double vb, double iso)
//	{
//		
//	}

}
