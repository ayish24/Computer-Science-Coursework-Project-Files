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
public class UpperCaseDecoratorTest {
    
    private UpperCaseDecorator<String> testDecorator;
    private ArrayList<String> testList;
    private Iterator<String> testIterator;
    
    @Before
    public void setUp() {
        testList = new ArrayList();
        testList.add("cat");
        testList.add("dog");
        testList.add("apple");
        testList.add("oops");
        
        testIterator = testList.iterator();
        testDecorator = new UpperCaseDecorator(testIterator);
    }

    @Test
    public void testNextWithEmptyString() {
        ArrayList<String> nullList = new ArrayList();
        nullList.add("");
        Iterator nullIterator = nullList.iterator();
        UpperCaseDecorator nullInstance = new UpperCaseDecorator(nullIterator);
        String expResult = "";
        String result = nullInstance.next().toString();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testNextWithSingleEntry() {
        ArrayList<String> singleEntryList = new ArrayList();
        singleEntryList.add("a");
        Iterator nullIterator = singleEntryList.iterator();
        UpperCaseDecorator nullInstance = new UpperCaseDecorator(nullIterator);
        String expResult = "A";
        String result = nullInstance.next().toString();
        
        assertEquals(expResult, result);
    }

    @Test
    public void testNextWithMultipleEntries() {
        String expectedResult = "CAT";
        String result = testDecorator.next();
        assertEquals(expectedResult, result);
        
        expectedResult = "DOG";
        result = testDecorator.next();
        assertEquals(expectedResult, result);
        
        expectedResult = "APPLE";
        result = testDecorator.next();
        assertEquals(expectedResult, result);
        
        expectedResult = "OOPS";
        result = testDecorator.next();
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testHasNextForNull() {
        ArrayList<String> nullList = new ArrayList();
        Iterator nullIterator = nullList.iterator();
        UpperCaseDecorator nullInstance = new UpperCaseDecorator(nullIterator);
        boolean expResult = false;
        boolean result = nullInstance.hasNext();
        
        assertEquals(expResult, result);
        assertFalse(nullInstance.hasNext());
    }
    
    @Test
    public void testHasNext() {
        assertTrue(testIterator.hasNext());
    }
    
    @Test
    public void testWithBinarySearchTreeIterator() {
        
    }
}