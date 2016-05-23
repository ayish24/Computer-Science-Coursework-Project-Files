package nosqldb;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    
    Database databaseInstance;
    Object actualResult;
    Object expectedResult;
    
    @Before
    public void setUp() throws Exception {
        
        databaseInstance = Database.getInstance();
        databaseInstance.put("testValue", 25);
    }

    @Test
    public void testGetInstance() throws Exception {
        String expResult = "{\"testValue\":25}";
        Database result = Database.getInstance();
        String actualresult = result.toString();
        assertEquals(expResult, actualresult);
    }

    @Test
    public void testGetCursor() throws Exception {
        Cursor cursor;
        cursor = databaseInstance.getCursor("testValue");
        actualResult = cursor.getInt();
        expectedResult = 25;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCreateTransaction() {
        Transaction newTransactionInstance = databaseInstance.createTransaction();
        assertTrue(newTransactionInstance.isActive());
        newTransactionInstance.abort();
        assertFalse(newTransactionInstance.isActive());
    }

    @Test
    public void testPut() {
        databaseInstance.put("newVal",34.5);
        actualResult = databaseInstance.getDouble("newVal");
        expectedResult = 34.5;
        assertEquals(expectedResult, actualResult);
        int statesSize = databaseInstance.getHistorySize();
        databaseInstance.rollBack(statesSize);
    }

    @Test
    public void testGet() {
        actualResult = databaseInstance.get("testValue");
        expectedResult = 25;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetInt() {
        actualResult = databaseInstance.getInt("testValue");
        expectedResult = 25;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetDouble() {
        databaseInstance.put("newVal",34.5);
        actualResult = databaseInstance.getDouble("newVal");
        expectedResult = 34.5;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetString() {
        databaseInstance.put("newVal","apple");
        actualResult = databaseInstance.getString("newVal");
        expectedResult = "apple";
        assertEquals(expectedResult, actualResult);
        databaseInstance.remove("newVal");
    }

    @Test
    public void testRemove() {
        databaseInstance.put("newVal","apple");
        Object result = databaseInstance.remove("newVal");
        actualResult = result.toString();
        expectedResult = "apple";
        assertEquals(expectedResult, actualResult);
        int statesSize = databaseInstance.getHistorySize();
        databaseInstance.rollBack(statesSize);
    }

    @Test
    public void testRollBack() {
        databaseInstance.put("newVal","apple");
        Object result = databaseInstance.remove("newVal");
        actualResult = result.toString();
        expectedResult = "apple";
        assertEquals(expectedResult, actualResult);
        int statesSize = databaseInstance.getHistorySize();
        databaseInstance.rollBack(statesSize);
    }

    @Test
    public void testToString() {
        actualResult = databaseInstance.toString();
        expectedResult = "{\"testValue\":25}";
        assertEquals(expectedResult, actualResult);
    }

    
}
