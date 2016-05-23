package assignment4.part2;

/**
 *
 * @author ayishwarya
 */
abstract class XMLObject {
    
    protected String text;
    protected XMLComposite xmlContainer;
    
    void accept (IXMLObjectVisitor visitor) {
       
    }
    
}
