package nosqldb;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayishwarya
 */
public class CursorObserverTest {
    Database databaseInstance;
    Cursor cursorInstance;
    
    @Before
    public void setUp() throws Exception {
        databaseInstance = Database.getInstance();
        databaseInstance.put("a", 10);
        databaseInstance.put("b", "hi");
        databaseInstance.put("c", 4.5);
        cursorInstance = databaseInstance.getCursor("a");
    }
    
    @Test
    public void testGetFromCursor() throws Exception {
        Object actualObjectResult = cursorInstance.get();
        Object expectedObjectResult = 10;
        assertEquals(expectedObjectResult,actualObjectResult);
        
        int actualIntResult = cursorInstance.getInt();
        int expectedIntResult = 10;
        assertEquals(expectedIntResult,actualIntResult);
        
        cursorInstance = databaseInstance.getCursor("c");
        Double actualDoubleResult = cursorInstance.getDouble();
        Double expectedDoubleResult = 4.5;
        assertEquals(expectedDoubleResult,actualDoubleResult);
        
        cursorInstance = databaseInstance.getCursor("b");
        String actualStringResult = cursorInstance.getString();
        String expectedStringResult = "hi";
        assertEquals(expectedStringResult,actualStringResult);
    }
    
    @Test
    public void testUpdateCursor() {
        Object actualObjectResult = cursorInstance.get();
        Object expectedObjectResult = 10;
        assertEquals(expectedObjectResult,actualObjectResult);
        
        databaseInstance.put("a", 20);
        actualObjectResult = cursorInstance.get();
        expectedObjectResult = 20;
        assertEquals(expectedObjectResult,actualObjectResult);
    }
    
    @Test
    public void testNotifyCursorObservers() throws Exception {
        databaseInstance.put("bar",10);
        cursorInstance = databaseInstance.getCursor("bar");
        
        new CursorObserver(cursorInstance);
        
        System.out.println("UPDATE # 1 : Change value to 20");
        databaseInstance.put("bar",20);
        
        System.out.println("UPDATE # 1 : Change value to 60");
        databaseInstance.put("bar",60);
        
    }

}
