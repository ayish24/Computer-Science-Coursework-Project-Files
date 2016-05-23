package nosqldb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class SnapshotRecoveryTest {
    
    private Database databaseInstance;
    
    @Before
    public void setUp() throws Exception {
        databaseInstance = Database.getInstance();
        List<String> array = new ArrayList<String>();
        Map<String,Object> object = new HashMap<String,Object>();
	array.add("hello, world");
        object.put("1", array);
        object.put("2", new HashMap<String,Object>());
        databaseInstance.put("a1", 1);
        databaseInstance.put("a2", 1.23);
        databaseInstance.put("b1", "hello");
        databaseInstance.put("c1", array);
        databaseInstance.put("d1", object);
    }
    
    public void readFromFile(String fileName) throws IOException {
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
    }

    @Test
    public void testSnapshotCreation() throws IOException {
        databaseInstance.snapshot();
        String fileName = "dbSnapshot.txt";
        System.out.println("Reading Snapshot File");
        readFromFile(fileName);
    }
    
   
}
