/*
 * Copyright (c) 2016 Ayishwarya Narasimhan
 */
package binarysearchtree;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayishwarya
 */
public class BinarySearchTreeTest {
    
    public BinarySearchTreeTest() {
    }
    
    /**
     * Testing a empty binary search tree : check for - null, empty and contains
     */
    @Test
    public void testBuildingEmptyBinarySearchTree() {
        BinarySearchTree testTree = new BinarySearchTree();
        assertNotNull(testTree);
        assertTrue(testTree.isEmpty());
        assertFalse(testTree.contains("A"));
    }
    
    /**
     * Testing tree for single node insertion by using contains() method
     */
    @Test
    public void testInsertingOneNode() {
        boolean expectedResult;
        String dataToInsert;
        String dataToCheck;
        BinarySearchTree testTree = new BinarySearchTree();
        dataToInsert = "Ashley";
        dataToCheck = "Ashley";
        testTree.add(dataToInsert);
        expectedResult = testTree.contains(dataToCheck);
        assertNotNull(testTree);
        assertTrue(expectedResult);
    }
    
    /**
     * Testing for multiple insert statements using contains() method
     */
    @Test
    public void testInsertingMultipleNodes() {
        boolean expectedResult;
        BinarySearchTree testTree = new BinarySearchTree();
        assertNotNull(testTree);

        testTree.add("Alan");
        expectedResult = testTree.contains("Alan");
        assertTrue(expectedResult);

        testTree.add("Mohan");
        expectedResult =testTree.contains("Mohan");
        assertTrue(expectedResult);

        testTree.add("Carl");
        expectedResult =testTree.contains("Carl");
        assertTrue(expectedResult);

        testTree.add("Wang");
        expectedResult =testTree.contains("Wang");
        assertTrue(expectedResult);

        testTree.add("James");
        expectedResult =testTree.contains("James");
        assertTrue(expectedResult);
    }
    
    /**
     * Testing for duplicate value insertion 
     * Note : Duplicates Allowed - inserted to the left sub tree
     */
    @Test
    public void testInsertingDuplicateValues() {
        List<String> expectedResult;
        List<String> actualResult;
        BinarySearchTree testTree = new BinarySearchTree(); 
        expectedResult = new ArrayList<>();
        
        expectedResult.add("Alan");
        expectedResult.add("Alan");
        
        testTree.add("Alan");
        testTree.add("Alan");
        actualResult = testTree.getAlphabeticOrderList();
        
        assertEquals(actualResult,expectedResult);
    }
    
    /**
     * Testing for printing all the nodes in alphabetical order using getAlphabeticOrderList() method
     * getAlphabeticOrderList() - returns an ordered list of all nodes in alphabetical order
     */
    @Test
    public void testPrintInAlphabeticalOrder() {
        List<String> expectedResult;
        List<String> actualResult;
        BinarySearchTree testTree = new BinarySearchTree(); 
        
        expectedResult = new ArrayList<>();
        expectedResult.add("Alan");
        expectedResult.add("Brady");
        expectedResult.add("Carl");
        expectedResult.add("andy");
        expectedResult.add("james");
        
        testTree.add("james");
        testTree.add("Carl");
        testTree.add("Alan");
        testTree.add("Brady");
        testTree.add("andy");
        actualResult = testTree.getAlphabeticOrderList();
        
        assertEquals(actualResult,expectedResult);
    }
    
    /**
     * Testing for printing all the nodes that begin with vowels in reverse alphabetical order
     * using getReverseAlphabeticVowelList() method
     * getReverseAlphabeticVowelList() - returns a list of nodes beginning with vowels in reverse order
     */
    @Test
    public void testPrintVowelReverseAlphabeticOrder() {
        List<String> expectedResult;
        List<String> actualResult;
        BinarySearchTree testTree = new BinarySearchTree(); 
        
        expectedResult = new ArrayList<>();
        expectedResult.add("otter");
        expectedResult.add("andy");
        expectedResult.add("Issac");
        expectedResult.add("Alan");
        
        testTree.add("otter");
        testTree.add("james");
        testTree.add("Carl");
        testTree.add("Alan");
        testTree.add("Brady");
        testTree.add("andy");
        testTree.add("Issac");
        actualResult = testTree.getReverseAlphabeticVowelList();
        
        assertEquals(actualResult,expectedResult);
    }
}
