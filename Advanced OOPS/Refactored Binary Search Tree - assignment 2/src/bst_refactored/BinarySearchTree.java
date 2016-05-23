/*
 * Advanced OOPS - Assignment 2
 */

package bst_refactored;

import java.util.*;

/**
 *
 * @author ayishwarya
 */


public class BinarySearchTree<T> extends AbstractCollection implements Iterable {

    private Node<T> root;
    private int size;
    private OrderingStrategy orderingMethod;
    private static NullNode leaf = new NullNode<>();

    /**
     * Node Classes
     * @param <T>
     */

    static class Node<T> {

        protected T value;
        protected Node<T> left;
        protected Node<T> right;

        Node() {
            value = null;
            left  = leaf;
            right = leaf;
        }

        Node(T t) {
            value = t;
            left  = leaf;
            right = leaf;
        }

        Node(T t, Node<T> l, Node<T> r) {
            value = t;
            left  = l;
            right = r;
        }

        protected void printInOrder() {
            left.printInOrder();
            System.out.print(value + " ");
            right.printInOrder();
        }

        public Node<T> addNode(T newValue, OrderingStrategy o) {
            return o.addNode(newValue, this);
        }

        @Override
        public String toString() {
            return ", " + value.toString();
        }
    }

    static class NullNode<T> extends Node {
        private NullNode() {
            value = null;
            left  = null;
            right = null;
        }

        @Override
        public Node<T> addNode(Object newValue, OrderingStrategy o) {
            return new Node(newValue);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void printInOrder() {
        }
    }

    /**
     * BinarySearchTree Classes and Collection function implementation
     */

    BinarySearchTree () {
        root = null;
        size = 0;
        orderingMethod = new NormalOrderStrategy();
    }

    BinarySearchTree (OrderingStrategy order) {
        root = null;
        size = 0;
        orderingMethod = order;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected void addNode(T newValue) {
        if (isEmpty()) {
            root = new Node<>(newValue);
        }
        else {
            root.addNode(newValue, orderingMethod);
        }
        size++;
    }

    @Override
    public boolean add(Object data) {
        addNode((T)data);
        return true;
    }

    @Override
    public boolean addAll(Collection dataElements) {
        dataElements.stream().forEach((object) -> {
            add(object);
        });
        return true;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        BinarySearchTreeIterator iterator = (BinarySearchTreeIterator<String>) iterator();

        String result = "[";

        while(iterator.hasNext()) {
            Node tmp = iterator.next();
            result += tmp.toString();
        }

        result = result.replaceFirst(",", "");

        return  result + "]";
    }

    @Override
    public BinarySearchTreeIterator<T> iterator() {
        return new BinarySearchTreeIterator<>(root);
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];

        BinarySearchTreeIterator iterator = new BinarySearchTreeIterator<>(root);

        Node<T> n = null;
        int i = 0;
        while(iterator.hasNext()) {
            n = iterator.next();
            if(n.value != null) {
                result[i] = n.value;
                i++;
            }
        }

        return  result;
    }

    @Override
    public T[] toArray(Object[] a) {
        if (a.length < size()) {
            a = toArray();
            return (T[])a;
        }

        BinarySearchTreeIterator iterator = new BinarySearchTreeIterator<>(root);

        Node<T> current = null;
        int i = 0;
        while(iterator.hasNext()) {
            current = iterator.next();
            if(current.value != null) {
                a[i] = current.value;
                i++;
            }
        }

        return (T[])a;
    }

    public void printInOrder() { root.printInOrder(); }

    public static void main(String[] args) {}
}