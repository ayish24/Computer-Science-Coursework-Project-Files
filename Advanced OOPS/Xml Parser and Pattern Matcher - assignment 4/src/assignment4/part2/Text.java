package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
public class Text extends XMLObject {
    
    Text (String currentText) {
        text = currentText;
    }
    
    @Override
    void accept (IXMLObjectVisitor visitor) {
        visitor.visit(this);
    }
    
}
