package nosqldb;

import java.util.Observable;

public class CursorObserver extends Observer{
    protected Cursor cursor;
    protected Object cursorValue;
    
    
    public CursorObserver(Cursor cursorObject) {
       this.cursor =  cursorObject;
       this.cursorValue = cursorObject.get();
       this.cursor.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println("Alert CursorObserver class !! : value now changed to " + cursor.get().toString());
    }
}
