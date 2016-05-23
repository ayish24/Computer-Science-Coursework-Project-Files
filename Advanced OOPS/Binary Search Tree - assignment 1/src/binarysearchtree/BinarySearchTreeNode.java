/*
 * Copyright (c) 2016 Ayishwarya Narasimhan
 */
package binarysearchtree;

/**
 *
 * @author ayishwarya
 */
public class BinarySearchTreeNode {
    
    private String nodeData;
    private BinarySearchTreeNode leftChild;
    private BinarySearchTreeNode rightChild;
    
    public BinarySearchTreeNode(String data) {
        this.nodeData = data;
    }
    
    public String getNodeData() {
        return nodeData;
    }
    
    public BinarySearchTreeNode getLeftChild() {
        return leftChild;
    }
    
    public void setLeftChild(BinarySearchTreeNode left) {
        this.leftChild = left;
    }
    
    public BinarySearchTreeNode getRightChild() {
        return rightChild;
    }
    
    public void setRightChild(BinarySearchTreeNode right) {
        this.rightChild = right;
    }
    
}
