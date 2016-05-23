package nosqldb;

import java.util.Map;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayishwarya
 */
public class ArrayOperationsTest {
    private JSONArray jsonArray;
    private JSONArray testJSONArray;
    private JSONArray testNumbersArray;
    private JSONArray testEmptyArray;
    private JSONArray testMultipleDataypeValuesArray;
    
    private int expectedArrayLength;
    private int actualArrayLength;
    private int index;
    private String expectedArrayContents;
    private String actualArrayContents;
    private String expectedResult;
    private String actualResult;
    private ArrayOperations testInstance;
    
    public ArrayOperationsTest() {
        jsonArray = new JSONArray();
        testJSONArray = new JSONArray();
        int[] arrayOfIntegers = {1,2,3};
        Object[] arrayOfMultipleDatatypes = {"hi",2,7.2,'a'};
        testNumbersArray = jsonArray.put(arrayOfIntegers);
        testMultipleDataypeValuesArray = new JSONArray();
        testMultipleDataypeValuesArray.put(arrayOfMultipleDatatypes);
    }

    @Test
    public void testPut() throws Exception {
        
        testInstance = new ArrayOperations(testNumbersArray);
        testInstance.put(4);
        expectedArrayLength = 2;
        expectedArrayContents = "[[1,2,3],4]";
        actualArrayContents = testInstance.toString();
        actualArrayLength = testInstance.length();
        assertEquals(expectedArrayContents,actualArrayContents);
        assertEquals(expectedArrayLength,actualArrayLength);
        
        testInstance = new ArrayOperations(testMultipleDataypeValuesArray);
        testInstance.put("bye");
        testInstance.put(2.4);
        int[] numbers = {1,2,3};
        testInstance.put(numbers);
        expectedArrayLength = 4;
        expectedArrayContents = "[[\"hi\",2,7.2,\"a\"],\"bye\",2.4,[1,2,3]]";
        actualArrayLength = testInstance.length();
        actualArrayContents = testInstance.toString();
        assertEquals(expectedArrayLength,actualArrayLength);
        assertEquals(expectedArrayContents,actualArrayContents);
    }

    @Test(expected=Exception.class)
    public void testPutNullValues() throws Exception {
        testInstance = new ArrayOperations(testEmptyArray);
        Map nullMap = null;
        testInstance.put(nullMap);
    }

    @Test
    public void testRemove() throws Exception {
        
        testInstance = new ArrayOperations(testNumbersArray);
        testInstance.put(1.2);
        testInstance.put(2.5);
        testInstance.put("cycle");
        expectedResult = "1.2";
        index = 1;
        String expectedContentsBeforeRemoval = "[[1,2,3],1.2,2.5,\"cycle\"]";
        int expectedLengthBeforeRemoval = 4;
        int expectedLengthAfterRemoval = 3;
        String expectedContentsAfterRemoval = "[[1,2,3],2.5,\"cycle\"]";
             
        actualArrayContents = testInstance.toString();
        actualArrayLength = testInstance.length();
        
        assertEquals(expectedContentsBeforeRemoval, actualArrayContents);
        assertEquals(expectedLengthBeforeRemoval, actualArrayLength);
        
        Object result = testInstance.remove(index);
        actualArrayContents = testInstance.toString();
        actualArrayLength = testInstance.length();
        actualResult = result.toString();
        
        assertEquals(expectedContentsAfterRemoval, actualArrayContents);
        assertEquals(expectedLengthAfterRemoval, actualArrayLength);
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void testRemoveWithNonExistentKey() throws Exception {
        testInstance = new ArrayOperations(testNumbersArray);
        expectedResult = null;
        index = 1;
        Object result = testInstance.remove(index);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGet() throws Exception {
        testInstance = new ArrayOperations(testNumbersArray);
        int[] values = {1,2,3};
        testInstance.put(1.3);
        testInstance.put("hi");
        testInstance.put(values);
        index = 1;
        expectedResult = "1.3";
        
        Object result = testInstance.get(index);
        actualResult = result.toString();
        assertEquals(expectedResult, actualResult);
        
        index = 2;
        expectedResult = "hi";
        
        result = testInstance.get(index);
        actualResult = result.toString();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetInt() throws Exception {
        int expResult;
        int result;
        
        testInstance = new ArrayOperations(testJSONArray);
        testInstance.put(1);
        testInstance.put(2);
        testInstance.put(3);
        testInstance.put(4);
        testInstance.put("apple");
        
        index = 1;
        expResult = 2;
        result = testInstance.getInt(index);
        assertEquals(expResult, result);
        
        index = 3;
        expResult = 4;
        result = testInstance.getInt(index);
        assertEquals(expResult, result);
    }
    
    @Test(expected=Exception.class)
    public void testGetIntWithOutOfBoundsException() throws Exception {
        testInstance = new ArrayOperations(testNumbersArray);
        testInstance.getInt(3);
        testInstance.getInt(1);
    }
    
    @Test(expected=Exception.class)
    public void testGetIntWithNotANumberAtIndex() throws Exception {
        testInstance = new ArrayOperations(testJSONArray);
        index = 1;
        testInstance.put(1);
        testInstance.put("apple");
        testInstance.getInt(index);
    }

    @Test
    public void testGetDouble() throws Exception {
        Double expResult;
        Double result;
        
        testInstance = new ArrayOperations(testJSONArray);
        testInstance.put(1.5);
        testInstance.put(2);
        testInstance.put(3.67);
        testInstance.put("hi");
        testInstance.put("apple");
        
        index = 2;
        expResult = 3.67;
        result = testInstance.getDouble(index);
        assertEquals(expResult, result);
    }
    
    @Test(expected=Exception.class)
    public void testGetDoubleWithOutOfBoundsException() throws Exception {
        testInstance = new ArrayOperations(testNumbersArray);
        testInstance.getDouble(3);
    }
    
    @Test(expected=Exception.class)
    public void testGetDoubleWithNotADoubleAtIndex() throws Exception {
        testInstance = new ArrayOperations(testJSONArray);
        index = 1;
        testInstance.put(1);
        testInstance.put("apple");
        testInstance.getDouble(index);
    }

    @Test
    public void testGetString() throws Exception {
        String expResult;
        String result;
        
        testInstance = new ArrayOperations(testJSONArray);
        testInstance.put(1.5);
        testInstance.put(2);
        testInstance.put(3.67);
        testInstance.put("hi");
        testInstance.put("apple");
        
        index = 3;
        expResult = "hi";
        result = testInstance.getString(index);
        assertEquals(expResult, result);
    }
    
    @Test(expected=Exception.class)
    public void testGetStringWithOutOfBoundsException() throws Exception {
        testInstance = new ArrayOperations(testNumbersArray);
        testInstance.getString(3);
    }
    
    @Test(expected=Exception.class)
    public void testGetStringWithNotAStringAtIndex() throws Exception {
        testInstance = new ArrayOperations(testJSONArray);
        index = 0;
        testInstance.put(1);
        testInstance.put("apple");
        testInstance.getString(index);
    }

    @Test
    public void testToString() throws Exception {
        testInstance = new ArrayOperations(testJSONArray);
        testInstance.put(2);
        testInstance.put(3.67);
        testInstance.put("hi");
        testInstance.put("apple");
        
        expectedResult = "[2,3.67,\"hi\",\"apple\"]";
        actualResult = testInstance.toString();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLength() throws Exception {
        testInstance = new ArrayOperations(testNumbersArray);
        testInstance.put(1.2);
        testInstance.put(2.5);
        testInstance.put("cycle");
        
        expectedArrayLength = 4;
        actualArrayLength = testInstance.length();
        assertEquals(expectedArrayLength, actualArrayLength);
    }
    
}
