package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
public class Header extends XMLObject {
    
    Header (String currentText) {
        text = currentText;
    }
    
    @Override
    void accept (IXMLObjectVisitor visitor) {
        visitor.visit(this);
    }
    
}
