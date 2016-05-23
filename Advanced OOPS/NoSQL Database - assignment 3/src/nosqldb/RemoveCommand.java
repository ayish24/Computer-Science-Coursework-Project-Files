package nosqldb;

import java.io.Serializable;

public class RemoveCommand extends Command implements Serializable{
    RemoveCommand(String tag) {
        
        Object result = Database.data.get(tag);

        executer = (IExecute & Serializable)() -> {
            Database.data.remove(tag);
            return result;
        };
        undoer   = (IUndo & Serializable)() -> {
            return Database.data.put(tag, result);
        };
        serializer = (ISerialize & Serializable)() -> {
            return (Database.REMOVE_ID + '\t'
                    + tag); };
    }
}
