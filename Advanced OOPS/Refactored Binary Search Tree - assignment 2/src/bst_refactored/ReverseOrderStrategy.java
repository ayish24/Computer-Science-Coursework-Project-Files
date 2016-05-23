/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

/**
 *
 * @author ayishwarya
 */
class ReverseOrderStrategy<T> extends OrderingStrategy {
    
    @Override
    public BinarySearchTree.Node<T> addNode(Object newValue, BinarySearchTree.Node currentNode) {
        if (((Comparable<Object>)newValue).compareTo(currentNode.value) >= 0) {
            
            currentNode.left = currentNode.left.addNode(newValue, this);
        }
        
        if (((Comparable<Object>)newValue).compareTo(currentNode.value) < 0) {
            
            currentNode.right = currentNode.right.addNode(newValue, this);
        }
        
        return currentNode;
    }
}
