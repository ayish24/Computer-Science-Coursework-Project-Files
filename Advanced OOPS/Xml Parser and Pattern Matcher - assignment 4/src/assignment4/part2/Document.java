package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
public class Document extends XMLObject {
    Document (String currentText) {
        text = currentText;
    }
    
    @Override
    void accept (IXMLObjectVisitor visitor) {
        visitor.visit(this);
    }
}
