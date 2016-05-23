package nosqldb;

import org.json.JSONObject;
import java.io.*;
import java.util.Observable;
import java.util.Observer;


public class Database extends Observable {
    protected static Database ourInstance;
    protected static JSONObject data = new JSONObject();
    protected static Memento history = new Memento();
    protected static PrintWriter commandFileInput;
    public static final String  PUT_ID    = "PUT",
                                REMOVE_ID = "REM";
    public static final String  INTEGER = "class java.lang.Integer",
                                DOUBLE  = "class java.lang.Double",
                                STRING  = "class java.lang.String",
                                OBJECT  = "class org.json.JSONObject",
                                ARRAY   = "class org.json.JSONArray";
    
    
    Database() throws Exception {
        try {
            recover();
        } catch (FileNotFoundException e) {
            System.out.println("Database file not found: Creating new dbfile...");
            history = new Memento();
            commandFileInput = new PrintWriter(new File("commands.txt"));
        } catch (ClassNotFoundException | IOException e) {
        }
    }

    public static Database getInstance() throws Exception {
        if (ourInstance == null) {
            ourInstance = new Database();
            ourInstance.history.restore();
        }
        return ourInstance;
    }
    
    protected int getHistorySize() {
        return this.history.states.size();
    }

    public Cursor getCursor(String key) throws Exception{
        Cursor newCursor = new Cursor(key);
        addObserver((Observer) newCursor);
        return newCursor;
    }

    public Transaction createTransaction() {
        int previousState = this.getHistorySize();
        return new Transaction(previousState);
    }

    public void put(String key, Object value) {
        PutCommand put = new PutCommand(key, value);
        history.store(put);
        put.execute();
        commandFileInput.println(put.serialize());
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public void put(String key, ArrayOperations value) {
        put(key, value.array);
    }

    public void put(String key, ObjectOperations value) {
        put(key, value.data);
    }

    public Object get(String key) {
        GetCommand get = new GetCommand(key);
        return (get).execute();
    }

    public Integer getInt(String key) {
        return (Integer)get(key);
    }

    public Double getDouble(String key) {
        return (Double)get(key);
    }

    public String getString(String key) { return (String)get(key); }

    public ObjectOperations getObject(String key) {
        return ObjectOperations.fromString((String)get(key));
    }

    public ArrayOperations getArray(String key) {
        return ArrayOperations.fromString((String)get(key));
    }

    public Object remove(String key) {
        RemoveCommand remove = new RemoveCommand(key);
        history.store(remove);

        commandFileInput.println(remove.serialize());
        Object result = remove.execute();

        setChanged();
        notifyObservers();
        clearChanged();

        return result;
    }

    public void rollBack(int statesSize) {
        history.rollBack(statesSize);
    }

    public void close() {
        commandFileInput.close();
    }

    public void snapshot() {
        snapshot(new File("commands.txt"), new File("dbSnapshot.txt"));
    }

    public void snapshot(File commands, File snapshot) {
        try {
            try (PrintWriter stateWriter = new PrintWriter(snapshot)) {
                stateWriter.print(data.toString());
            }

            commandFileInput = new PrintWriter(commands);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recover() throws FileNotFoundException, IOException,
            ClassNotFoundException, Exception {
        recover(new File("commands.txt"), new File("dbSnapshot.txt"));
    }

    public void recover(File commands, File snapshot) throws FileNotFoundException, IOException,
            ClassNotFoundException, Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader(snapshot));
        String currentLine;

        String savedState = "";
        while((currentLine = fileReader.readLine() ) != null) {
            savedState += currentLine;
        }

        data = new JSONObject(savedState);

        fileReader = new BufferedReader(new FileReader(commands));

        while((currentLine = fileReader.readLine() ) != null) {
            Command currentCommand = Command.deserializeCommand(currentLine);
            currentCommand.execute();
            history.store(currentCommand);
        }

        commandFileInput = new PrintWriter(new FileWriter(commands, true));
    }


    @Override
    public String toString() {
        return data.toString();
    }

    public static void main(String[] args) throws Exception{
    }
}
