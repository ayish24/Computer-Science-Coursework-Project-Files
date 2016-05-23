package nosqldb;

import org.json.JSONObject;

public class ObjectOperations {
    JSONObject data;
        
    ObjectOperations() {
        data = new JSONObject();
    }

    ObjectOperations(JSONObject j) {
        data = j;
    }

    public JSONObject put(String tag, Object o) throws Exception {
        if (o == null) {
            throw new Exception();
        }
        data.put(tag, o);
        return data;
    }

    public Object get(String tag) {
        return data.get(tag);
    }

    public int getInt(String tag) {
        return data.getInt(tag);
    }

    public double getDouble(String tag) {
        return data.getDouble(tag);
    }
    
    public static ObjectOperations fromString(String newData) {
        return new ObjectOperations(new JSONObject(newData));
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
