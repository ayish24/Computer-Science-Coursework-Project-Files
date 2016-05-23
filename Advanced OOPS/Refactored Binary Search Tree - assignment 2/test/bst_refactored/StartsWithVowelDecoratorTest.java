/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayishwarya
 */
public class StartsWithVowelDecoratorTest {
    
    private StartsWithVowelDecorator<String> testDecorator, nullDecorator;
    private ArrayList<String> testList, nullList;
    private Iterator<String> testIterator, nullIterator;
    
    @Before
    public void setUp() {
        testList = new ArrayList();
        testList.add("cat");
        testList.add("dog");
        testList.add("apple");
        testList.add("oops");
        testIterator = testList.iterator();
        testDecorator = new StartsWithVowelDecorator(testIterator); 
        
        nullList = new ArrayList();
        nullIterator = nullList.iterator();
        nullDecorator = new StartsWithVowelDecorator(nullIterator);
    }
    
    @Test
    public void testHasNext() {
        assertTrue(testDecorator.hasNext());
    }
    
    @Test
    public void testHasNextForNull() {
        assertFalse(nullDecorator.hasNext());
    }

    @Test
    public void testNextWithSingleEntry() {
        testList = new ArrayList();
        testList.add("Baloon");
        testList.add("elephant");
        testIterator = testList.iterator();
        testDecorator = new StartsWithVowelDecorator(testIterator);
        
        String expectedResult = "elephant";
        String result = testDecorator.next();
        assertEquals(result, expectedResult);
        
        result = testDecorator.next();
        assertNull(result);
    }
    
    @Test
    public void testNextWithMultipleEntries() {
        String expectedResult = "apple";
        String result = testDecorator.next();
        assertEquals(result, expectedResult);
        
        expectedResult = "oops";
        result = testDecorator.next();
        assertEquals(result, expectedResult);
    }
}

