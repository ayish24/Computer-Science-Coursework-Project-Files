package nosqldb;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayishwarya
 */

public class ObjectOperationsTest {
    
    ObjectOperations objectOperationInstance;
    
    @Before
    public void setUp() throws Exception {
        objectOperationInstance = new ObjectOperations();
    }

    @Test
    public void testPut() throws Exception {
        Object expectedResult = "{\"a\":1}";
        Object actualResult = objectOperationInstance.put("a", 1).toString();
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGet() throws Exception {
        objectOperationInstance.put("testValue", 25);
        Object expectedResult = 25;
        Object actualResult = objectOperationInstance.get("testValue");
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetInt() throws Exception {
        objectOperationInstance.put("testValue", 25);
        int expectedResult = 25;
        int actualResult = objectOperationInstance.getInt("testValue");
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetDouble() throws Exception {
        objectOperationInstance.put("testValue", 2.5);
        Double expectedResult = 2.5;
        Double actualResult = objectOperationInstance.getDouble("testValue");
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testToString() throws Exception {
        objectOperationInstance.put("testValue", 2.5);
        Double expectedResult = 2.5;
        Double actualResult = objectOperationInstance.getDouble("testValue");
        assertEquals(expectedResult,actualResult);
        
        String expectedStringResult = "{\"testValue\":2.5}";
        String actualStringResult = objectOperationInstance.toString();
        assertEquals(expectedStringResult,actualStringResult);
    }
    
}
