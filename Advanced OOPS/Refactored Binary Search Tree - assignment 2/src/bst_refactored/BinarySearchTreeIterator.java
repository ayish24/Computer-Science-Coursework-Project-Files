/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

import bst_refactored.BinarySearchTree.Node;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author ayishwarya
 */
public class BinarySearchTreeIterator<T> implements Iterator{
    Stack<Node<T>> treeNodes;

    public BinarySearchTreeIterator(Node<T> root) {
        treeNodes = new Stack<>();
        BinarySearchTree.Node<T> currentNode = root;

        while (currentNode != null) {
            treeNodes.push(currentNode);
            currentNode = currentNode.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !treeNodes.isEmpty();
    }

    @Override
    public BinarySearchTree.Node<T> next() {
        BinarySearchTree.Node<T> currentNode = treeNodes.pop();
        BinarySearchTree.Node<T> result = currentNode;
        if (currentNode.right != null) {
            currentNode = currentNode.right;
            while (currentNode != null) {
                treeNodes.push(currentNode);
                currentNode = currentNode.left;
            }
        }
        return result;
    }
    
    public String currentValue() {
        return treeNodes.peek().value.toString();
    }
}
