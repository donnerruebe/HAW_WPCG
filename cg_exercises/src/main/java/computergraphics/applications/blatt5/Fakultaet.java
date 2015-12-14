package computergraphics.applications.blatt5;

public class Fakultaet
{
	public static int f(int v)
	{
		if (v > 1)
		{
			return v * (Fakultaet.f(v-1));
		}
		else
		{
			return 1;
		}
	}

}
