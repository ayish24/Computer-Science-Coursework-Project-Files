package assignment4.part2;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author ayishwarya
 */
public class XMLComposite {
    
    XMLObject currentXMLObject;
    Vector<XMLComposite> children;
    
    XMLComposite (XMLObject newObject) {
        currentXMLObject = newObject;
        children = new Vector<>();
        newObject.xmlContainer = this;
    }
    
    void addElement (XMLComposite newCompositeElement) {
        children.add(newCompositeElement);
    }
    
    void removeElement (XMLComposite newCompositeElement) {
        children.remove(newCompositeElement);
    }
    
    XMLComposite getChildAtIndex(int index) {
        return children.elementAt(index);
    }
    
    void printChildren() {
        Iterator XMLCompositeIterator = children.iterator();
        Vector<String> childrenElements = new Vector<>();
        String currentChild;
        while (XMLCompositeIterator.hasNext()) {
            currentChild = XMLCompositeIterator.next().toString();
            childrenElements.add(currentChild);
            System.out.println(currentChild);
        }
    }
    
}
