package computergraphics.applications.blatt5;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;

public class CurveNode extends Node
{
	private Curve c;

	public CurveNode(Curve c)
	{
		this.c = c;
	}

	@Override
	public void drawGl(GL2 gl)
	{
		// TODO Auto-generated method stub
		gl.glBegin(GL2.GL_LINES);
		Vector3 v = c.calculate(0);
		Vector3 v1 = c.calculate(0);
		gl.glVertex3d(v.get(0),v.get(1),v.get(2));
		for (double i = 0.05; i <= 1; i += 0.05)
		{
			v = c.calculate(i);
			gl.glVertex3d(v.get(0),v.get(1),v.get(2));
			
			gl.glVertex3d(v.get(0),v.get(1),v.get(2));
		}
		v = c.calculate(1);
		gl.glVertex3d(v.get(0),v.get(1),v.get(2));
		gl.glEnd();
	}
	
	
	
}
