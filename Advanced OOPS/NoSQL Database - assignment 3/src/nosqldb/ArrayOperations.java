package nosqldb;

import org.json.JSONArray;

public class ArrayOperations {
    protected JSONArray array;
        
    ArrayOperations() {
        array = new JSONArray();
    }

    ArrayOperations(JSONArray j) {
        array = j;
    }

    public void put(Object o) throws Exception {
        if (o == null) {
            throw new Exception();
        }
        array.put(o);
    }

    public Object remove(int index) {
        Object arrayValue;
        int maxIndexSize = array.length()-1;
        if(index > maxIndexSize) {
            return null;
        }
        else {
            arrayValue = array.get(index);
            array.remove(index);
            return arrayValue;
        }
    }

    public Object get(int index) {
        rangeCheck(index);
        return array.get(index);
    }

    public int getInt(int index) {
        rangeCheck(index);
        return array.getInt(index);
    }

    public double getDouble(int index) {
        rangeCheck(index);
        return array.getDouble(index);
    }
        
    public String getString(int index) {
        rangeCheck(index);
        return array.getString(index);
    }

    public ArrayOperations getArray(int index) {
        rangeCheck(index);
        return new ArrayOperations(array.getJSONArray(index));
    }

    public ObjectOperations getObject(int index) {
        rangeCheck(index);
        return new ObjectOperations(array.getJSONObject(index));
    }

    public static ArrayOperations fromString(String newData) {
        return new ArrayOperations(new JSONArray(newData));
    }

        @Override
        public String toString() {
            return array.toString();
        }
        
        public int length() {
            return array.length();
        }
        
        private void rangeCheck(int index) {
            if (index >= array.length())
                throw new ArrayIndexOutOfBoundsException(index);
        }

}
