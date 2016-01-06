package computergraphics.applications.blatt7;

import java.security.InvalidParameterException;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;;

public class EbeneNode extends Node
{
	// Punkt (Stützvektor) der Ebene
	public final Vector3 point;
	// Normale der Ebene
	public final Vector3 normal;
	// Tagentialvektoren tU und tV, werden anhand point und normal berechnet
	private final Vector3 tU;
	private final Vector3 tV;
	

	public EbeneNode(Vector3 point, Vector3 normal)
	{
		super();
		this.point = point;
		this.normal = normal;
		
		// Ab hier werden die UV-Parameter berechnet. Dazu werden zwei Werte frei gewaehlt, der dritte wird dann berechnet
		double free_choosen_1 = point.get(0) - 1;
		double free_choosen_2 = point.get(1) - 1;
		
		// Fallunterscheidung, falls die Normale 0-Werte enthält. Zunächst wird versucht nach Z aufzulösen, dann nach Y, dann nach X
		if (normal.get(2) != 0)
		{
			tU = new Vector3(free_choosen_1,free_choosen_2,(0 - normal.get(0) * free_choosen_1 - normal.get(1) * free_choosen_2) / normal.get(2));
		}
		else if (normal.get(1) != 0)
		{
			tU = new Vector3(free_choosen_1,(0 - normal.get(0) * free_choosen_1 - normal.get(2) * free_choosen_2) / normal.get(1),free_choosen_2);
		}
		else if (normal.get(0) != 0)
		{
			tU = new Vector3((0 - normal.get(1) * free_choosen_1 - normal.get(2) * free_choosen_2) / normal.get(0),free_choosen_1,free_choosen_2);
		}
		else
		{
			// Fehlermeldung, wenn alle Werte der Normale = 0 sind, dann können U und V nicht berechnet werden (und es ist auch keine gültige Normale)
			throw new InvalidParameterException("Invalid normal given: " + normal);
		}
		
		// Normen von tU und tV
		tU.normalize();
		// tV aus Kreuzprodukt von tU und Normalen berechnen
		tV = normal.cross(tU).getNormalized();
	}
	
	@Override
	public IntersectionResult findIntersection(Node object, Ray3D r)
	{
		double lambda = (this.normal.multiply(this.point) - this.normal.multiply(r.getPoint())) / this.normal.multiply(r.getDirection());
		// Pruefen, ob es ein gueltiges Lambda gibt (negativ ist auch ungültig, da der Strahl nur in eine Richtung gehen soll und nicht rückwärts)
		if (Double.isNaN(lambda) || lambda <= 0)
		{
			return null;
		}
		Vector3 point = r.getPoint().add(r.getDirection().multiply(lambda));
		// Erzeuge neue IntersectionResult, da gültiger Schnittpunkt gefunden
		IntersectionResult result = new IntersectionResult();
		result.point = point;
		result.object = this;
		result.normal = this.normal.getNormalized();
		return result;
	}
	
	/**
	 * Ermittelt zu einem gegebenen Punkt in der Ebene die Farbe des jeweiligen Feldes (dunkelgrau oder weiß)
	 * @param point
	 * @return
	 */
	public Vector3 getChessboardColor(Vector3 point)
	{
		double u = point.multiply(tU);
		double v = point.multiply(tV);
		if (u < 0) u = -u + 1;
		if (v < 0) v = -v + 1;
		if ((int)u % 2 == (int)v % 2)
		{
			return new Vector3(1, 1, 1);
		}
		else
		{
			return new Vector3(.2, .2, .2);
		}
	}


	@Override
	public void drawGl(GL2 gl)
	{
		// Nicht implemeniert, da hier nicht relevant
	}

}
