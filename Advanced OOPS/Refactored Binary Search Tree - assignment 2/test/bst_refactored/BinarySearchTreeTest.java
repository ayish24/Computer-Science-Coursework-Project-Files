/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;


import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ayishwarya
 */
public class BinarySearchTreeTest {
    BinarySearchTree<String> testInstance;
    
    @Before
    public void setUp() {
         testInstance = new BinarySearchTree<>();
    }
    
    @Test
    public void testAdd() throws Exception {
        assertTrue(testInstance.add("B"));
        assertTrue(testInstance.add("cat"));
    }
    
    @Test
    public void testAddAll() throws Exception {
        List<String> list = new ArrayList();
        list.add("Apple");
        list.add("Frog");
        list.add("Game");
        assertTrue(testInstance.addAll(list));
    }

    @Test
    public void testToString() throws Exception {
        testInstance.add("B");
        testInstance.add("A");
        testInstance.add("D");
        testInstance.add("C");
        testInstance.add("E");
        assertEquals( testInstance.toString(), "[ A, B, C, D, E]");
    }

    @Test
    public void testIterator() throws Exception {
        testInstance.add("C");
        testInstance.add("A");
        testInstance.add("B");
        testInstance.add("E");
        testInstance.add("D");

        BinarySearchTreeIterator bsti = testInstance.iterator();

        while(bsti.hasNext()) {
            assertNotNull(bsti.next());
        }
    }

    @Test
    public void testToArray() throws Exception {
        testInstance.add("B");
        testInstance.add("D");
        testInstance.add("C");
        testInstance.add("E");
        testInstance.add("A");

        Object[] result = testInstance.toArray();
        Object[] expectedResult = new Object[]{"A", "B", "C", "D", "E"};

        assertArrayEquals(result, expectedResult);
    }   
}
