/*
 * Advanced OOPS - Assignment 2
 */
package bst_refactored;

import java.util.Iterator;

/**
 *
 * @author ayishwarya 
 * @param <String> 
 */
public class StartsWithVowelDecorator<String> implements Iterator {
    private Iterator<String> iterator;
    
    public StartsWithVowelDecorator() {};
    
    public StartsWithVowelDecorator(Iterator<String> i) {
        iterator = i;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public String next() {
        String result;
        
        while(iterator.hasNext()) {
            result = iterator.next();
            if((result.toString()).matches("[AEIOUaeiou]+.*"))
                return result;
        }
        return null;
    }
    
}
