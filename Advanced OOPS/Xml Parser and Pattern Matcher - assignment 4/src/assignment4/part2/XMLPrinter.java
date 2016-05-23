package assignment4.part2;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileReader;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ayishwarya
 */
public class XMLPrinter extends DefaultHandler {
    private XMLComposite root;
    private String recentTag;
    private Stack<XMLComposite> encounterOrder = new Stack<>();

    public XMLPrinter(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            XMLReader xmlr = XMLReaderFactory.createXMLReader();
            xmlr.setContentHandler(this);
            xmlr.setErrorHandler(this);

            FileReader fr = new FileReader(fileName);
            xmlr.parse(new InputSource(fr));
        }
        catch (Throwable t) {}
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        recentTag = localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        encounterOrder.pop();
        recentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(recentTag != null) {
            if(root == null) {
                root = xmlObjectFactory(recentTag, new String(ch, start, length));
                encounterOrder.push(root);
            }
            else {
                XMLComposite newComposite = xmlObjectFactory(recentTag, new String(ch, start, length));
                if(!encounterOrder.empty())
                    encounterOrder.peek().addElement(newComposite);
                else
                    encounterOrder.addElement(newComposite);
                    
                encounterOrder.push(newComposite);
            }
        }
    }

    private XMLComposite xmlObjectFactory(String tag, String value) {
        switch (tag) {
            case "CS635Document":
                return new XMLComposite(new Document(value));
            case "header":
                return new XMLComposite(new Header(value));
            case "text":
                return new XMLComposite(new Text(value));
            default:
                return null;
        }
    }

    public void printHeaders() {
        PrintHeaderVisitor hpv = new PrintHeaderVisitor();
        visitAll(root, hpv);
    }

    public void printHtml() {
        PrintHTMLVisitor phv = new PrintHTMLVisitor();
        root.currentXMLObject.accept(phv);
    }

    private void visitAll(XMLComposite composite, IXMLObjectVisitor visitor) {
        if(composite.children.isEmpty()) {
            composite.currentXMLObject.accept(visitor);
        }
        else {
            composite.currentXMLObject.accept(visitor);
            for(XMLComposite c : composite.children) {
                visitAll(c, visitor);
            }
        }
    }

    public static void main(String[] args) {
        XMLPrinter xmlPrinter = new XMLPrinter("example.xml");
        xmlPrinter.printHtml();
        xmlPrinter.printHeaders();
        
        try {
            xmlPrinter.startElement(null,"text", null, null);
        } catch (SAXException ex) {
            Logger.getLogger(XMLPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        char[] ch = new char[8];
        ch[0] = 'w';
        ch[1] = 'e';
        ch[2] = 'l';
        ch[3] = 'c';
        ch[4] = 'o';
        ch[5] = 'm';
        ch[6] = 'e';
        
        System.out.println(new String(ch, 0, 4));
        
        try {
            xmlPrinter.characters(ch, 0, 4);
        } catch (SAXException ex) {
            Logger.getLogger(XMLPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            xmlPrinter.endElement(null, "text", null);
        } catch (SAXException ex) {
            Logger.getLogger(XMLPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("After adding characters");
       
        xmlPrinter.printHtml();
        
    }
}
