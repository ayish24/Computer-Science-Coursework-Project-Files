/*
 * Copyright (c) 2016 Ayishwarya Narasimhan
 * 
 */
package binarysearchtree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayishwarya narasimhan
 */
    public class BinarySearchTree {

    private BinarySearchTreeNode root; 
    
    /**
     * Returns true if this binary search tree contains no elements
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Returns true if this binary search tree contains the element dataToCheck
     */
    public boolean contains(String dataToCheck) {
        if (dataToCheck == null) {
            return false;
        }
        return contains(root, dataToCheck);
    }
    
    private boolean contains(BinarySearchTreeNode node, String dataToCheck) {
        if (node != null) {
            BinarySearchTreeNode currentNode = root;
            while (currentNode != null) {
                if (node.getNodeData().compareTo(currentNode.getNodeData()) == 0) {
                    return true;
                }
                else if (node.getNodeData().compareTo(currentNode.getNodeData()) <= 0) {
                    currentNode = currentNode.getLeftChild();
                }
                else {
                    currentNode = currentNode.getRightChild();
                }
            }    
        }
        return false;
    }
        
    /**
     * Inserts the incoming new node to tree at the appropriate location
     * @param data - new value to be inserted into the tree
     */
    public void add(String data) {
        BinarySearchTreeNode nodeToAdd = new BinarySearchTreeNode(data);
        if (root == null) {
            root = nodeToAdd;
            return;
        }
        // Case 2 : If otherwise, new node is created and added to the correct spot by travesing the tree
        traverseAndInsertNode(root, nodeToAdd);
    }
    
    private void traverseAndInsertNode(BinarySearchTreeNode nodeToCompare, BinarySearchTreeNode nodeToAdd) {
        // Case 2.1 : Node with equal or lower value than the compared node will go to the left sub tree 
        if (nodeToAdd.getNodeData().compareTo(nodeToCompare.getNodeData()) <= 0) {
            // If left child is empty , then set the new node to the left spot
            if(nodeToCompare.getLeftChild() == null) {
                nodeToCompare.setLeftChild(nodeToAdd);
            }
            // Continue to traverse down the left sub tree 
            else {
                traverseAndInsertNode(nodeToCompare.getLeftChild(), nodeToAdd);
            }
        }
        
        // Case 2.2 : Node with greater value than the compared node will go to the right sub tree
        if (nodeToAdd.getNodeData().compareTo(nodeToCompare.getNodeData()) >0) {
            // If right child is empty , then set the new node to the right spot
            if(nodeToCompare.getRightChild() == null) {
                nodeToCompare.setRightChild(nodeToAdd);
            }
            // Continue to traverse down the right sub tree
            else {
                traverseAndInsertNode(nodeToCompare.getRightChild(), nodeToAdd);
            }
        }
    }
    
    /**
     * Prints out all the nodes in the binary search tree in alphabetical order
     * Note : it is case sensitive, Upper case text will be printed first followed by the lower case ones
     */
    public void printInAlphabeticOrder() {
        List<String> alphabeticalOrderList;
        alphabeticalOrderList = this.getAlphabeticOrderList();
        System.out.println("All Nodes in Alphabetical Order \t" + alphabeticalOrderList);
    }
    

    /**
     * Generates alphabetical order list of node by traversing the binary search tree in the following order :
     * Left -> Node -> Right i.e. In-order Traversal
     * @return alphabetically ordered list containing all the node data.
     */
    public List<String> getAlphabeticOrderList() {
        if (root == null) {
            return null;
        }
        List<String> alphabeticalOrderList = new ArrayList<>();
        getAlphabeticOrderList(root, alphabeticalOrderList);
        return alphabeticalOrderList;
    }

    private void getAlphabeticOrderList(BinarySearchTreeNode node, List<String> nodeValues) {
        // first goes to left sub tree
        if (node.getLeftChild() != null) {
            getAlphabeticOrderList(node.getLeftChild(), nodeValues);
        }
        // goes to this node next 
        if(node.getNodeData() != null) {
            nodeValues.add(node.getNodeData());
        }
        // next it goes through right sub tree
        if (node.getRightChild() != null) {
            getAlphabeticOrderList(node.getRightChild(), nodeValues);
        }
    }

    
    private boolean startsWithVowel(String nodeData) {
        char startingCharacter = nodeData.toLowerCase().charAt(0);
        return startingCharacter == 'a' || 
               startingCharacter == 'e' || 
               startingCharacter == 'i' || 
               startingCharacter == 'o' || 
               startingCharacter == 'u';
    }
    
    /**
     * Prints out all the nodes that start with vowel in reverse alphabetical order
     * Note : since its reverse order , lower case text are printed first followed by the uppercase
     */
    public void printVowelInReverseAlphabeticOrder() {
        List<String> reverseAlphabeticVowelList;
        reverseAlphabeticVowelList = this.getReverseAlphabeticVowelList();
        System.out.println("Vowels in Reverse Alphabetical Order \t" + reverseAlphabeticVowelList);
    }
    
    /**
     * Generates a list of node data that starts with a vowel in reverse alphabetical order
     * by performing Post Order Traversal  : Right -> Node -> Left 
     * @return reverse alphabetically ordered vowel list  
     */
    public List<String> getReverseAlphabeticVowelList() {
        if (root == null) {
            return null;
        }
        List<String> reverseAlphabeticalOrderVowelList = new ArrayList<>();
        getReverseAlphabeticVowelList(root, reverseAlphabeticalOrderVowelList);
        return reverseAlphabeticalOrderVowelList;
    }

    private void getReverseAlphabeticVowelList (BinarySearchTreeNode node, List<String> nodeValues) {
        // first goes to right sub tree
        if (node.getRightChild() != null)
            getReverseAlphabeticVowelList(node.getRightChild(), nodeValues);
        // goes to this node next and adds to list only if it starts with Vowel
        String nodeData = node.getNodeData();
        if(nodeData != null) {
            if (startsWithVowel(nodeData)) {
            nodeValues.add(nodeData);
            }
        }
        // finally it goes through the left sub tree
        if (node.getLeftChild() != null)
            getReverseAlphabeticVowelList(node.getLeftChild(), nodeValues);
    }

    public static void main(String[] args) {
        
    }
}
    

