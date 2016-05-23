package nosqldb;

import java.sql.Array;
import static nosqldb.Database.data;
import static nosqldb.Database.history;

class Transaction {
    protected boolean isActive;
    protected Memento commands;
    private int rollBackToStateSize;

    Transaction(int previousState) {
        isActive = true;
        commands = new Memento();
        rollBackToStateSize = previousState - 1;
    }

    public void put(String key, Object value) {
        commands.store(new PutCommand(key, value));
    }

    private Object get(String key) {
        GetCommand get = new GetCommand(key);
        commands.store(get);
        return get.execute();
    }

    public Integer getInt(String key) {
        return (Integer)get(key);
    }

    public Double getDouble(String key) {
        return (Double)get(key);
    }

    public String getString(String key) { 
        return (String)get(key); 
    }

    public Array getArray(String key) {
        return (Array)get(key);
    }

    public Object getObject(String key) {
        return get(key);
    }

    public Object remove(String key) {
        return commands.store(new RemoveCommand(key));
    }

    public boolean isActive() {
        return isActive;
    }

    protected void abort() {
        history.rollBack(rollBackToStateSize);
        isActive = false;
    }

    protected void commit() {
        try {
            commands.playBack(0, commands.size());
            history.states.addAll(commands.states);
            isActive = false;
        }
        catch (Exception e) {
            abort();
        }
    }
        
    public int getRollBackToStateSize() {
        return rollBackToStateSize;
    }
        
    public void setRollBackToStateSize(int statesSize) {
        this.rollBackToStateSize = statesSize - 1;
    }
        
    @Override
    public String toString() {
        return data.toString();
    }
}

   

