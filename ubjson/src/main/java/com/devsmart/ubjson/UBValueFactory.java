package com.devsmart.ubjson;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class UBValueFactory {

    private static final UBNull VALUE_NULL = new UBNull();
    private static final UBBool VALUE_TRUE = new UBBool(true);
    private static final UBBool VALUE_FALSE = new UBBool(false);

    public static UBNull createNull() {
        return VALUE_NULL;
    }

    public static UBChar createChar(char value) {
        return new UBChar(value);
    }

    public static UBBool createBool(boolean value) {
        if (value) {
            return VALUE_TRUE;
        } else {
            return VALUE_FALSE;
        }
    }

    static boolean inRange(long value, long min, long max) {
        return value >= min && value <= max;
    }

    public static UBValue createNumber(Number value) {
        if(value instanceof Integer) {
            return createInt(value.longValue());
        } else {
            return createFloat64(value.doubleValue());
        }
    }

    public static UBValue createInt(long value) {
        if (inRange(value, 0, 255)) {
            return new UBUInt8(value);
        } else if (inRange(value, -128, 127)) {
            return new UBInt8(value);
        } else if (inRange(value, -32768, 32767)) {
            return new UBInt16(value);
        } else if (inRange(value, -2147483648, 2147483647)) {
            return new UBInt32(value);
        } else {
            return new UBInt64(value);
        }
    }

    public static UBFloat32 createFloat32(float value) {
        return new UBFloat32(value);
    }

    public static UBFloat64 createFloat64(double value) {
        return new UBFloat64(value);
    }

    public static UBString createString(byte[] string) {
        return new UBString(string);
    }

    public static UBString createString(String string) {
        return createString(string.getBytes(UBString.UTF_8));
    }

    public static UBValue createStringOrNull(String string) {
        if (string == null) {
            return createNull();
        } else {
            return createString(string);
        }

    }

    public static UBInt8Array createArray(boolean[] value) {
        byte[] data = new byte[value.length];
        for(int i=0;i<data.length;i++) {
            data[i] = (byte) (value[i] ? 1 : 0);
        }
        return createArray(data);
    }

    public static UBValue createArrayOrNull(boolean[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBInt8Array createArray(byte[] value) {
        return new UBInt8Array(value, false);
    }

    public static UBInt8Array createUnsignedArray(byte[] value) {
        return new UBInt8Array(value, true);
    }

    public static UBValue createArrayOrNull(byte[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBInt16Array createArray(short[] value) {
        return new UBInt16Array(value);
    }

    public static UBValue createArrayOrNull(short[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBInt32Array createArray(int[] value) {
        return new UBInt32Array(value);
    }

    public static UBValue createArrayOrNull(int[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBInt64Array createArray(long[] value) {
        return new UBInt64Array(value);
    }

    public static UBValue createArrayOrNull(long[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBFloat32Array createArray(float[] value) {
        return new UBFloat32Array(value);
    }

    public static UBValue createArrayOrNull(float[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBFloat64Array createArray(double[] value) {
        return new UBFloat64Array(value);
    }

    public static UBValue createArrayOrNull(double[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBStringArray createArray(String[] value) {
        return new UBStringArray(value);
    }

    public static UBValue createArrayOrNull(String[] value) {
        if(value == null) {
            return createNull();
        } else {
            return createArray(value);
        }
    }

    public static UBArray createArray(UBValue... args) {
        return new UBArray(args);
    }

    public static UBArray createArray(Iterable values) {
        ArrayList<UBValue> newArray = new ArrayList<UBValue>();
        for(Object obj : values) {
            newArray.add(createValue(obj));
        }
        return createArray(newArray.toArray(new UBValue[newArray.size()]));
    }

    public static UBObject createObject(Map map) {
        TreeMap<String, UBValue> newMap = new TreeMap<String, UBValue>();
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            if(!(e.getKey() instanceof String)) {
                throw new IllegalArgumentException("key is not a String");
            } else {
                newMap.put((String) e.getKey(), createValue(e.getValue()));
            }
        }

        return new UBObject(newMap);
    }

    public static UBObject createObject() {
        return new UBObject();
    }

    public static UBValue createValueOrNull(UBValue value) {
        if(value == null) {
            return createNull();
        } else {
            return value;
        }
    }

    public static UBValue createValue(Object obj) {
        if(obj == null) {
            return createNull();
        } else if(obj instanceof UBValue) {
            return (UBValue) obj;
        } else if(obj instanceof Boolean){
            return createBool(((Boolean) obj));
        } else if(obj instanceof Integer) {
            return createInt(((Integer) obj).longValue());
        } else if(obj instanceof Double) {
            return createFloat64(((Double) obj));
        } else if(obj instanceof Float) {
            return createFloat32(((Float) obj));
        } else if(obj instanceof String) {
            return createString(((String) obj));
        } else if(obj instanceof Map) {
            return createObject((Map) obj);
        } else if(obj instanceof Iterable) {
            return createArray((Iterable) obj);
        } else {
            throw new IllegalArgumentException("unknown object type: " + obj.getClass().getSimpleName());
        }
    }
}
