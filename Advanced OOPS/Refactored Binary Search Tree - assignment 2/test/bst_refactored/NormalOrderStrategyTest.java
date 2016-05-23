/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayishwarya
 */
public class NormalOrderStrategyTest {
    
    BinarySearchTree testInstance;
    
    @Before
    public void setUp() {
        testInstance = new BinarySearchTree();
    }
    
    

    /**
     * Test of addNode method, of class NormalOrderStrategy.
     */
    @Test
    public void testDefaultAddNodeStrategy() {
        testInstance.add("A");
        testInstance.add("D");
        testInstance.add("C");
        testInstance.add("F");
        testInstance.add("B");
        testInstance.add("E");
        
        String expectedResult = "[ A, B, C, D, E, F]";
        String result = testInstance.toString();
        assertEquals(expectedResult,result);
    }
    
    @Test
    public void testAddNodeWithNormalStrategySet() {
        
        NormalOrderStrategy normalOrder = new NormalOrderStrategy();
        BinarySearchTree<String> testNormalInstance = new BinarySearchTree(normalOrder);
        
        testNormalInstance.add("V");
        testNormalInstance.add("G");
        testNormalInstance.add("I");
        testNormalInstance.add("A");
        testNormalInstance.add("S");
        
        String expectedResult = "[ A, G, I, S, V]";
        String result = testNormalInstance.toString();
        assertEquals(expectedResult,result);
    }
    
}
