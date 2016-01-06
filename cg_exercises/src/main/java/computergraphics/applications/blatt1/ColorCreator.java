package computergraphics.applications.blatt1;

import computergraphics.math.Vector3;

public class ColorCreator
{

	public static Vector3 createColor(float r, float g, float b)
	{
		return new Vector3(r/255,g/255,b/255);
	}
}
