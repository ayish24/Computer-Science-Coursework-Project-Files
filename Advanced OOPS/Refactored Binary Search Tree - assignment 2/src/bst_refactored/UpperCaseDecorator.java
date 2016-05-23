/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

import java.util.Iterator;

/**
 *
 * @author ayishwarya
 */
class UpperCaseDecorator<String> implements Iterator{
    private Iterator<String> iterator;
    
    public UpperCaseDecorator() {};

    public UpperCaseDecorator(Iterator<String> i) {
        iterator = i;
    }
    
    @Override
    public String next() {
        String result = iterator.next();
        String upperCaseResult = (String) result.toString().toUpperCase();
        return upperCaseResult;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

}


