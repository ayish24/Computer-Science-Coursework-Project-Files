/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ayishwarya
 */
public class BinarySearchTreeIteratorTest {
    
    private BinarySearchTreeIterator bstIterator;
    private BinarySearchTreeIterator nullBstIterator;
    private BinarySearchTree bstInstance;
    private BinarySearchTree nullBstInstance;
    
    @Before
    public void setUp() {
        bstInstance = new BinarySearchTree<>();
        bstInstance.add("C");
        bstInstance.add("A");
        bstInstance.add("B");
        bstInstance.add("E");
        bstInstance.add("D");
        bstIterator = bstInstance.iterator();
        
        nullBstInstance = new BinarySearchTree<>();
        nullBstIterator = nullBstInstance.iterator();
    }

    /**
     * Test of hasNext method, of class BinarySearchTreeIterator.
     */
    @Test
    public void testHasNext() {
        boolean expectedResult = true;
        boolean result = bstIterator.hasNext();
        assertEquals(expectedResult, result);
        
        expectedResult = false;
        result = nullBstIterator.hasNext();
        assertEquals(expectedResult, result);
    }

    /**
     * Test of next method, of class BinarySearchTreeIterator.
     */
    @Test
    public void testNext() {
        String expectedResult = "A";
        bstIterator.next();
        String result = bstIterator.currentValue();
        assertEquals(expectedResult, result);
        
        
    }
    
    @Test(expected = java.util.EmptyStackException.class)
    public void testForEmptyStackException() {
        while(bstIterator.hasNext())
            bstIterator.next();
        
        bstIterator.next();
        bstIterator.currentValue();
    }
   
    
}
