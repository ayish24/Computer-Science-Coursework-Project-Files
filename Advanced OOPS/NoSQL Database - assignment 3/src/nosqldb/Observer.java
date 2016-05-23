package nosqldb;

public abstract class Observer {
    protected Cursor cursor;
    public abstract void update();
}
