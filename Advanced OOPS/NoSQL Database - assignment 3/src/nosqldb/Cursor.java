package nosqldb;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import static nosqldb.Database.data;

class Cursor extends Observable implements Observer{
    private Object observingValue;
    private final String key;
    private List<nosqldb.Observer> observers = new ArrayList<nosqldb.Observer>();

    Cursor(String newKey) throws Exception {
        key = newKey;
        observingValue = data.get(key);
    }

    public Object get(){
        return observingValue;
    }

    public Integer getInt() {
        return (Integer)observingValue;
    }

    public Double getDouble() {
        return (Double)observingValue;
    }

    public String getString() {
        return (String)observingValue;
    }

    public Object getObject() {
        return (Object)observingValue;
    }

    public Array getArray() {
        return (Array)observingValue;
    }
        
    public void addObserver(nosqldb.Observer observingObject) {
            observers.add(observingObject);
    }

    public void removeObserver(nosqldb.Observer observingObject) {
            observers.remove(observingObject);
    }
    
    @Override
    public synchronized void addObserver(Observer observingObject) {
        super.addObserver(observingObject);
    }

    public synchronized void removeObserver(Observer observingObject) {
        super.deleteObserver(observingObject);
    }

    @Override
    public void update(Observable o, Object arg) {
        observingValue = data.get(key);
        setChanged();
        notifyAllObservers();
        clearChanged();
    }

    public void notifyAllObservers() {
        for(nosqldb.Observer observer : observers) {
            observer.update();
        }
    }
}
        

        
    
