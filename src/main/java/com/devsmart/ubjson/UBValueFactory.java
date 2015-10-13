package com.devsmart.microdb.ubjson;


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
        if(value) {
            return VALUE_TRUE;
        } else {
            return VALUE_FALSE;
        }
    }

    static boolean inRange(long value, long min, long max) {
        return value >= min && value <= max;
    }

    public static UBValue createInt(long value) {
        if(inRange(value, 0, 255)) {
            return new UBUInt8(value);
        } else if(inRange(value, -128, 127)) {
            return new UBInt8(value);
        } else if(inRange(value, -32768, 32767)) {
            return new UBInt16(value);
        } else if(inRange(value, -2147483648, 2147483647)) {
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
        if(string == null) {
            return createNull();
        } else {
            return createString(string);
        }

    }

    public static UBInt8Array createArray(byte[] value) { return new UBInt8Array(value); }

    public static UBInt16Array createArray(short[] value) { return new UBInt16Array(value); }

    public static UBInt32Array createArray(int[] value) {
        return new UBInt32Array(value);
    }

    public static UBFloat32Array createArray(float[] value) {
        return new UBFloat32Array(value);
    }

    public static UBFloat64Array createArray(double[] value) {
        return new UBFloat64Array(value);
    }

    public static UBArray createArray(UBValue... args) {
        return new UBArray(args);
    }

    public static UBObject createObject(TreeMap<String, UBValue> value) {
        return new UBObject(value);
    }
}
