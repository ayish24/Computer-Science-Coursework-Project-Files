package nosqldb;

import java.util.Hashtable;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    
    private Database databaseInstance;
    private Transaction newTransaction;
    private boolean transactionStatus;
    private String expectedResult;
    private String actualResult;
    private String expectedResultBeforeTransaction;
    private String actualResultBeforeTransaction;
    private String expectedResultAfterTransaction;
    private String actualResultAfterTransaction;
    
    @Before
    public void setUp() throws Exception {
        databaseInstance= Database.getInstance();
        int[] numbers = {1,2,3};
        Hashtable objectValue = new Hashtable();
        objectValue.put("name", "Ayish");
        databaseInstance.put("a", "apple");
        databaseInstance.put("b", "banana");        
        databaseInstance.put("c", 6.78);
        databaseInstance.put("d", 8);
        databaseInstance.put("e", 10);
        databaseInstance.put("f",numbers);
        databaseInstance.put("object",objectValue);
    }
    
    @Test
    public void testPut() {
        newTransaction =databaseInstance.createTransaction();
        databaseInstance.put("e", 10);
        newTransaction.commit();

        int result = newTransaction.getInt("e");
        int expResult = 10;
        assertEquals(expResult,result);
    }

    @Test
    public void testGetInt() {
        newTransaction =databaseInstance.createTransaction();
        int result = newTransaction.getInt("d");
        int expResult = 8;
        assertEquals(expResult,result);
    }

    @Test
    public void testGetDouble() {
        newTransaction =databaseInstance.createTransaction();
        Double result = newTransaction.getDouble("c");
        Double expResult = 6.78;
        assertEquals(expResult,result);
    }

    @Test
    public void testGetString() {
        newTransaction =databaseInstance.createTransaction();
        actualResult = newTransaction.getString("a");
        expectedResult = "apple";
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetObject() {
        newTransaction =databaseInstance.createTransaction();
        Object result = newTransaction.getObject("object");
        actualResult = result.toString();
        expectedResult = "{name=Ayish}";
        assertEquals(expectedResult,actualResult);
    }

    @Test(expected=Exception.class)
    public void testRemove() {
        newTransaction =databaseInstance.createTransaction();
        newTransaction.remove("b");
        newTransaction.remove("a");
        newTransaction.remove("c");
        newTransaction.remove("e");
        newTransaction.remove("f");
        newTransaction.remove("object");
        newTransaction.commit();
        databaseInstance.get("b");
        
    }

    @Test
    public void testIsActive() {
        newTransaction =databaseInstance.createTransaction();
        transactionStatus = newTransaction.isActive();
        assertTrue(transactionStatus);
        newTransaction.abort();
        
        transactionStatus = newTransaction.isActive();
        assertFalse(transactionStatus);
        
        newTransaction =databaseInstance.createTransaction();
        transactionStatus = newTransaction.isActive();
        assertTrue(transactionStatus);
        newTransaction.commit();
       
        transactionStatus = newTransaction.isActive();
        assertFalse(transactionStatus);
    }

    @Test
    public void testAbort() {
        expectedResultBeforeTransaction = "8";
        actualResultBeforeTransaction =databaseInstance.get("d").toString();
        assertEquals(expectedResultBeforeTransaction, actualResultBeforeTransaction);
        
        expectedResultBeforeTransaction = "{\"a\":\"apple\",\"b\":\"banana\",\"c\":6.78,\"d\":8,\"e\":10,\"f\":[1,2,3],\"testValue\":25,\"object\":{\"name\":\"Ayish\"}}";
        actualResultBeforeTransaction =databaseInstance.toString();
        assertEquals(expectedResultBeforeTransaction, actualResultBeforeTransaction);
        
        newTransaction =databaseInstance.createTransaction();
        newTransaction.put("f", 20);
        newTransaction.remove("d");
        newTransaction.put("g", "Canada");
        newTransaction.abort();
        
        transactionStatus = newTransaction.isActive();
        assertFalse(transactionStatus);
        
        expectedResultAfterTransaction = expectedResultBeforeTransaction;
        actualResultAfterTransaction =databaseInstance.toString();
        assertEquals(expectedResultAfterTransaction, actualResultAfterTransaction);
    }

   @Test
    public void testCommit() {
        expectedResultBeforeTransaction = "{\"a\":\"apple\",\"b\":\"banana\",\"c\":6.78,\"d\":8,\"e\":10,\"f\":[1,2,3],\"testValue\":25,\"object\":{\"name\":\"Ayish\"}}";
        actualResultBeforeTransaction =databaseInstance.toString();
        assertEquals(expectedResultBeforeTransaction, actualResultBeforeTransaction);
        
        newTransaction =databaseInstance.createTransaction();
        newTransaction.put("g", 20);
        newTransaction.remove("d");
        newTransaction.put("h", "Canada");
        newTransaction.commit();
        
        transactionStatus = newTransaction.isActive();
        assertFalse(transactionStatus);
        
        expectedResultAfterTransaction = "{\"a\":\"apple\",\"b\":\"banana\",\"c\":6.78,\"e\":10,\"f\":[1,2,3],\"g\":20,\"h\":\"Canada\",\"testValue\":25,\"object\":{\"name\":\"Ayish\"}}";
        actualResultAfterTransaction =databaseInstance.toString();
        assertEquals(expectedResultAfterTransaction, actualResultAfterTransaction);
    }
}
