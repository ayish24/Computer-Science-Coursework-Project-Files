package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
public interface IXMLObjectVisitor {
    
    void visit(Document currentDocument);
    void visit(Header currentHeader);
    void visit(Text currentText);
    void visit(XMLObject currentXMLObject);
    
}
