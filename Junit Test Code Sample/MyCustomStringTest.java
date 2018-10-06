package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(7, mycustomstring.countNumbers());
    }
    
    /**This test checks whether method countNumbers suitably throws an NullPointerException if the current string is null*/
    @Test(expected = NullPointerException.class)
    public void testCountNumbers2() {
    	mycustomstring.setString(null);
    	mycustomstring.countNumbers();
    }

    /**This test checks whether method countNumbers functions properly to get the exact number of digits in the current string */
    @Test
    public void testCountNumbers3() {
    	mycustomstring.setString("002I'd b3tt3r put789 s0me321 d161ts in this 5tr1n6, right?");
        assertEquals(10, mycustomstring.countNumbers());
    }

    /**This test checks whether method countNumbers treats a contiguous sequence of digits as a number */
    @Test
    public void testCountNumbers4() {
    	mycustomstring.setString("Ilikenumber0123456789 ");
        assertEquals(1, mycustomstring.countNumbers());
    }

    /**This test checks whether method countNumbers returns zero if current string is empty */
    @Test
    public void testCountNumbers5() {
    	mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }

    /**This test checks whether method countNumbers returns zero if there is no digit in current string  */
    @Test
    public void testCountNumbers6() {
    	mycustomstring.setString("I have no digit");
        assertEquals(0, mycustomstring.countNumbers());
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("d33p md1  i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("'bt t0 6snh r6rh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably throws an NullPointerException if the current string is null and "n" is greater than zero*/
    @Test(expected = NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd3() {
    	mycustomstring.setString(null);
    	mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,true);
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably throws an IllegalArgumentException if "n" is less than zero*/
    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd4() {
    	mycustomstring.setString("h2dh45dh hd1h2d");
    	mycustomstring.getEveryNthCharacterFromBeginningOrEnd(-1,false);
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably throws an IllegalArgumentException if "n" equals to zero*/
    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd5() {
    	mycustomstring.setString("h2dh45dh hd1h2d");
    	mycustomstring.getEveryNthCharacterFromBeginningOrEnd(0,true);
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd returns empty string if current string is empty */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd6() {
    	mycustomstring.setString("");
    	assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd returns empty string if current string is empty */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd7() {
    	mycustomstring.setString("");
    	assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, false));
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd returns empty string if current string has less than n characters */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd8() {
    	mycustomstring.setString("I have 20 characters");
    	assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(22, false));
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd returns empty string if current string has less than n characters */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd9() {
    	mycustomstring.setString("I have 20 characters");
    	assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(22, true));
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd returns full string if n equals to 1 */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd10() {
    	mycustomstring.setString("I have exactly 28 characters");
    	assertEquals("I have exactly 28 characters", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, true));
    }

    /**This test checks whether method getEveryNthCharacterFromBeginningOrEnd returns full string if n equals to 1 */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd11() {
    	mycustomstring.setString("I have exactly 28 characters");
    	assertEquals("I have exactly 28 characters", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, false));
    }

    /**This test checks whether the characters in the returning string of method getEveryNthCharacterFromBeginningOrEnd is in the same order and with the same case as in the current string*/
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd12() {
    	mycustomstring.setString("ALLUPPERCASE");
    	assertEquals("LPCE", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    /**This test checks whether the characters in the returning string of method getEveryNthCharacterFromBeginningOrEnd is in the same order and with the same case as in the current string*/
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd13() {
    	mycustomstring.setString("alllowercase");
    	assertEquals("lwce", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    /**This test checks whether the characters in the returning string of method getEveryNthCharacterFromBeginningOrEnd is in the same order and with the same case as in the current string*/
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd14() {
    	mycustomstring.setString("ALLUPPERCASE");
    	assertEquals("AUEA", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    @Test
    public void testConvertDigitsToNamesInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    /**This test checks whether method convertDigitsToNamesInSubstring throws IllegalArgumentException if startPoint is greater than endPointer*/
    @Test(expected=IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring2() {
    	mycustomstring.convertDigitsToNamesInSubstring(5,2);
    }

    /**This test checks whether method convertDigitsToNamesInSubstring throws NullPointerException if "startPosition" < "endPosition", "startPosition" and "endPosition" are greater than 0, and the current string is null*/
    @Test(expected=NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring3() {
    	mycustomstring.setString(null);
    	mycustomstring.convertDigitsToNamesInSubstring(2,5);
    }

    /**This test checks whether method convertDigitsToNamesInSubstring throws NullPointerException if "startPosition" = "endPosition", "startPosition" and "endPosition" are greater than 0, and the current string is null*/
    @Test(expected=NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring4() {
    	mycustomstring.setString(null);
    	mycustomstring.convertDigitsToNamesInSubstring(2,2);
    }

    /**This test checks whether method convertDigitsToNamesInSubstring throws MyIndexOutOfBoundsException if "startPosition" <= "endPosition", "startPosition" < 1*/
    @Test(expected=MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring5() {
    	mycustomstring.setString("startPointer out of bounds");
    	mycustomstring.convertDigitsToNamesInSubstring(0,5);
    }

    /**This test checks whether method convertDigitsToNamesInSubstring throws MyIndexOutOfBoundsException if "startPosition" <= "endPosition", "endPosition" > string.length()*/
    @Test(expected=MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring6() {
    	mycustomstring.setString("endPointer out of bounds");
    	mycustomstring.convertDigitsToNamesInSubstring(1,40);
    }

    /**This test checks whether method convertDigitsToNamesInSubstring converts all the digits to English names*/
    @Test
    public void testConvertDigitsToNamesInSubstring7() {
    	mycustomstring.setString("1234");
    	mycustomstring.convertDigitsToNamesInSubstring(1,4);
        assertEquals("OneTwoThreeFour", mycustomstring.getString());
    }

    /**This test checks whether method convertDigitsToNamesInSubstring does nothing if there is no digit in the current string*/
    @Test
    public void testConvertDigitsToNamesInSubstring8() {
    	mycustomstring.setString("I have no digit");
    	mycustomstring.convertDigitsToNamesInSubstring(1,8);
        assertEquals("I have no digit", mycustomstring.getString());
    }

}

