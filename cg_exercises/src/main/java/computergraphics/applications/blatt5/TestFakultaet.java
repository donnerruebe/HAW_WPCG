package computergraphics.applications.blatt5;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestFakultaet
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testF()
	{
		assertEquals(120, Fakultaet.f(5));
		assertEquals(3628800, Fakultaet.f(10));
		assertEquals(6, Fakultaet.f(3));
		assertEquals(2, Fakultaet.f(2));
		assertEquals(1, Fakultaet.f(1));
		assertEquals(1, Fakultaet.f(0));
	}

}
