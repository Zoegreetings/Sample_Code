

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BuggyClassTestBC3 {

	private BuggyClass buggyClass;
	@Before
    public void setUp() {
		buggyClass = new BuggyClass();
    }

    @After
    public void tearDown() {
    	buggyClass = null;
    }
	
	/*This test only covers the branch of (y>0), but does not reveals the fault.*/
    @Test
	public void testbuggyMethod3_1() {
		assertEquals(3, buggyClass.buggyMethod3(2));
	}
    /*This test only covers the branch of (y<0), but does not reveals the fault.*/
	@Test
	public void testbuggyMethod3_2() {
		assertEquals(-1, buggyClass.buggyMethod3(-2));
	}
	/*This test only covers the branch of (y=0), but does not reveals the fault.*/
	@Test
	public void testbuggyMethod3_3() {
		assertEquals(0, buggyClass.buggyMethod3(0));
	}
}
