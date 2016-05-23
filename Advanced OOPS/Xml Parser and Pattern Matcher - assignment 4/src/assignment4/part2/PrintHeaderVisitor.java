package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
public class PrintHeaderVisitor implements IXMLObjectVisitor {
    
    @Override
    public void visit(Document document) {

    }

    @Override
    public void visit(Header header) {
        System.out.println("<header>" + header.text + "</header>");
    }

    @Override
    public void visit(Text text) {

    }

    @Override
    public void visit(XMLObject xmlObject) {
        xmlObject.accept(this);
    }
}