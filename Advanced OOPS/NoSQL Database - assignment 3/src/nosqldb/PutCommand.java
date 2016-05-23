package nosqldb;

import java.io.Serializable;

public class PutCommand extends Command implements Serializable{
    PutCommand(String tag, Object value) {
        executer   = (IExecute & Serializable)() -> Database.data.put(tag, value);
        undoer     = (IUndo & Serializable)() -> Database.data.remove(tag);
        serializer = (ISerialize & Serializable)() -> {
            return (Database.PUT_ID + '\t'
                    + tag + '\t'
                    + value.getClass().toString() + '\t'
                    + value.toString()); };
    }
}
