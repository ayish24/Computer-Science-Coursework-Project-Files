package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
public class PrintHTMLVisitor implements IXMLObjectVisitor {
    
    @Override
    public void visit(Document document) {
        System.out.print("<CS635Document>" + document.text);

        if(!document.xmlContainer.children.isEmpty()) {
            for(XMLComposite c : document.xmlContainer.children) {
                visit(c.currentXMLObject);
            }
        }

        System.out.println("</CS635Document>");
    }

    @Override
    public void visit(Header header) {
        System.out.print("<header>" + header.text);

        if(!header.xmlContainer.children.isEmpty()) {
            for(XMLComposite c : header.xmlContainer.children) {
                visit(c.currentXMLObject);
            }
        }

        System.out.println("</header>");
    }

    @Override
    public void visit(Text text) {
        System.out.print("<text>" + text.text);

        if(!text.xmlContainer.children.isEmpty()) {
            for(XMLComposite c : text.xmlContainer.children) {
                visit(c.currentXMLObject);
            }
        }

        System.out.println("</text>");
    }

    @Override
    public void visit(XMLObject xmlObject) {
        xmlObject.accept(this);
    }
}
