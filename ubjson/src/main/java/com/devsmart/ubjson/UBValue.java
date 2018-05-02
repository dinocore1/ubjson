package com.devsmart.ubjson;

import java.io.Serializable;
import java.util.Comparator;

public abstract class UBValue implements Comparable<UBValue>, Serializable {

    public static final byte MARKER_NULL = 'Z';
    public static final byte MARKER_TRUE = 'T';
    public static final byte MARKER_FALSE = 'F';
    public static final byte MARKER_CHAR = 'C';
    public static final byte MARKER_INT8 = 'i';
    public static final byte MARKER_UINT8 = 'U';
    public static final byte MARKER_INT16 = 'I';
    public static final byte MARKER_INT32 = 'l';
    public static final byte MARKER_INT64 = 'L';
    public static final byte MARKER_FLOAT32 = 'd';
    public static final byte MARKER_FLOAT64 = 'D';
    public static final byte MARKER_STRING = 'S';
    public static final byte MARKER_ARRAY_START = '[';
    public static final byte MARKER_ARRAY_END = ']';
    public static final byte MARKER_OBJ_START = '{';
    public static final byte MARKER_OBJ_END = '}';
    public static final byte MARKER_OPTIMIZED_TYPE = '$';
    public static final byte MARKER_OPTIMIZED_SIZE = '#';
    private static final long serialVersionUID = -7268494408231769421L;

    public enum Type {
        Null,
        Char,
        Bool,
        Int8,
        Uint8,
        Int16,
        Int32,
        Int64,
        Float32,
        Float64,
        String,
        Array,
        Object
    }

    public abstract Type getType();
    //public abstract void write(OutputStream out) throws IOException;

    public boolean isNull() {
        return getType() == Type.Null;
    }

    public boolean isBool() {
        return getType() == Type.Bool;
    }

    public boolean asBool() {
        return ((UBBool)this).getBool();
    }

    public boolean isChar() {
        return getType() == Type.Char;
    }

    public char asChar() {
        return ((UBChar)this).getChar();
    }

    public boolean isNumber() {
        switch (getType()){
            case Int8:
            case Uint8:
            case Int16:
            case Int32:
            case Int64:
            case Float32:
            case Float64:
                return true;

            default:
                return false;
        }
    }

    public boolean isInteger() {
        switch (getType()){
            case Int8:
            case Uint8:
            case Int16:
            case Int32:
            case Int64:
                return true;

            default:
                return false;
        }
    }

    public boolean isString() {
        return getType() == Type.String;
    }

    public String asString() {
        if(this.isNull()) {
            return null;
        } else {
            UBString thiz = (UBString) this;
            return thiz.getString();
        }
    }

    public byte[] asByteArray() {
        if(isNull()) {
            return null;
        } else if(isString()) {
            return ((UBString) this).asByteArray();
        } else {
            return ((UBInt8Array) this).getValues();
        }
    }

    public byte asByte() {
        return (byte)asInt();
    }

    public short asShort() {
        return (short)asInt();
    }

    public int asInt() {
        switch (getType()){
            case Int8:
                return ((UBInt8)this).getInt();
            case Uint8:
                return ((UBUInt8)this).getInt();
            case Int16:
                return ((UBInt16)this).getInt();
            case Int32:
                return ((UBInt32)this).getInt();
            case Int64:
                return (int)((UBInt64)this).getInt();
            case Float32:
                return (int)((UBFloat32)this).getFloat();
            case Float64:
                return (int)((UBFloat64)this).getDouble();
            case String:
                return Integer.parseInt(asString());

            default:
                throw new RuntimeException("not a number type");

        }
    }

    public long asLong() {
        switch (getType()){
            case Bool:
                return asBool() ? 1 : 0;
            case Char:
                return asChar();
            case Int8:
                return ((UBInt8)this).getInt();
            case Uint8:
                return ((UBUInt8)this).getInt();
            case Int16:
                return ((UBInt16)this).getInt();
            case Int32:
                return ((UBInt32)this).getInt();
            case Int64:
                return (long)((UBInt64)this).getInt();
            case Float32:
                return (long)((UBFloat32)this).getFloat();
            case Float64:
                return (long)((UBFloat64)this).getDouble();
            case String:
                return Long.parseLong(asString());

            default:
                throw new RuntimeException("not a number type");

        }
    }

    public float asFloat32() {
        switch (getType()) {
            case Float32:
                return ((UBFloat32)this).getFloat();
            case Float64:
                return (float)((UBFloat64)this).getDouble();
            case Int8:
                return ((UBInt8)this).getInt();
            case Uint8:
                return ((UBUInt8)this).getInt();
            case Int16:
                return ((UBInt16)this).getInt();
            case Int32:
                return ((UBInt32)this).getInt();
            case Int64:
                return ((UBInt64)this).getInt();
            case String:
                return Float.parseFloat(asString());

            default:
                throw new RuntimeException("not a float type");
        }
    }

    public double asFloat64() {
        switch (getType()) {
            case Float32:
                return ((UBFloat32)this).getFloat();
            case Float64:
                return ((UBFloat64)this).getDouble();
            case Int8:
                return ((UBInt8)this).getInt();
            case Uint8:
                return ((UBUInt8)this).getInt();
            case Int16:
                return ((UBInt16)this).getInt();
            case Int32:
                return ((UBInt32)this).getInt();
            case Int64:
                return ((UBInt64)this).getInt();
            case String:
                return Double.parseDouble(asString());

            default:
                throw new RuntimeException("not a float type");
        }
    }

    public boolean isArray() {
        return getType() == Type.Array;
    }

    public UBArray asArray() {
        return ((UBArray)this);
    }

    public int size() {
        int retval;
        switch (getType()) {
            case Array:
                retval = asArray().size();
                break;

            case String:
                retval = ((UBString)this).length();
                break;

            default:
                retval = -1;
        }

        return retval;
    }

    /**
     * Interprets a strongly-typed number array to array of booleans.
     * If a value found in the array is greater than 0 return true, false otherwise.
     * @return booleans
     */
    public boolean[] asBoolArray() {
        boolean[] retval;
        UBArray array = asArray();
        switch(array.getStrongType()){
            case Int8: {
                byte[] data = ((UBInt8Array) array).getValues();
                retval = new boolean[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i] > 0;
                }
                break;
            }

            case Int16: {
                short[] data = ((UBInt16Array) array).getValues();
                retval = new boolean[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i] > 0;
                }
                break;
            }

            case Int32: {
                int[] data = ((UBInt32Array)array).getValues();
                retval = new boolean[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i] > 0;
                }
                break;
            }

            case Int64: {
                long[] data = ((UBInt64Array)array).getValues();
                retval = new boolean[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i] > 0;
                }
                break;
            }

            case Float32: {
                float[] data = ((UBFloat32Array) array).getValues();
                retval = new boolean[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i] > 0;
                }
                break;
            }

            case Float64: {
                double[] data = ((UBFloat64Array) array).getValues();
                retval = new boolean[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i] > 0;
                }
                break;
            }


            default:
                throw new RuntimeException("not an int32[] type");
        }

        return retval;
    }

    public short[] asShortArray() {
        short[] retval;
        UBArray array = asArray();
        switch(array.getStrongType()){
            case Int8: {
                byte[] data = ((UBInt8Array) array).getValues();
                retval = new short[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int16: {
                retval = ((UBInt16Array) array).getValues();
                break;
            }

            case Int32: {
                int[] data = ((UBInt32Array) array).getValues();
                retval = new short[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (short) data[i];
                }
                break;
            }

            case Int64: {
                long[] data = ((UBInt64Array) array).getValues();
                retval = new short[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (short) data[i];
                }
                break;
            }

            case Float32: {
                float[] data = ((UBFloat32Array) array).getValues();
                retval = new short[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (short) data[i];
                }
                break;
            }

            case Float64: {
                double[] data = ((UBFloat64Array) array).getValues();
                retval = new short[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (short) data[i];
                }
                break;
            }


            default:
                throw new RuntimeException("not an int32[] type");
        }

        return retval;
    }

    public int[] asInt32Array() {
        int[] retval;
        UBArray array = asArray();
        switch(array.getStrongType()){
            case Int8: {
                byte[] data = ((UBInt8Array) array).getValues();
                retval = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int16: {
                short[] data = ((UBInt16Array) array).getValues();
                retval = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int32: {
                retval = ((UBInt32Array)array).getValues();
                break;
            }

            case Int64: {
                long[] data = ((UBInt64Array) array).getValues();
                retval = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (int) data[i];
                }
                break;
            }

            case Float32: {
                float[] data = ((UBFloat32Array) array).getValues();
                retval = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (int) data[i];
                }
                break;
            }

            case Float64: {
                double[] data = ((UBFloat64Array) array).getValues();
                retval = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (int) data[i];
                }
                break;
            }


            default:
                throw new RuntimeException("not an int32[] type");
        }

        return retval;
    }

    public long[] asInt64Array() {
        long[] retval;
        UBArray array = asArray();
        switch(array.getStrongType()){
            case Int8: {
                byte[] data = ((UBInt8Array) array).getValues();
                retval = new long[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int16: {
                short[] data = ((UBInt16Array) array).getValues();
                retval = new long[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int32: {
                int[] data = ((UBInt32Array) array).getValues();
                retval = new long[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int64: {
                retval = ((UBInt64Array) array).getValues();
                break;
            }

            case Float32: {
                float[] data = ((UBFloat32Array) array).getValues();
                retval = new long[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (long) data[i];
                }
                break;
            }

            case Float64: {
                double[] data = ((UBFloat64Array) array).getValues();
                retval = new long[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (long) data[i];
                }
                break;
            }


            default:
                throw new RuntimeException("not an int32[] type");
        }

        return retval;
    }

    public float[] asFloat32Array() {
        float[] retval;
        UBArray array = asArray();
        switch(array.getStrongType()){
            case Int8: {
                byte[] data = ((UBInt8Array) array).getValues();
                retval = new float[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int16: {
                short[] data = ((UBInt16Array) array).getValues();
                retval = new float[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int32: {
                int[] data = ((UBInt32Array) array).getValues();
                retval = new float[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int64: {
                long[] data = ((UBInt64Array) array).getValues();
                retval = new float[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Float32:
                retval = ((UBFloat32Array)array).getValues();
                break;

            case Float64: {
                double[] data = ((UBFloat64Array) array).getValues();
                retval = new float[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = (float) data[i];
                }
                break;
            }


            default:
                throw new RuntimeException("not an float32[] type");
        }

        return retval;
    }

    public double[] asFloat64Array() {
        double[] retval;
        UBArray array = asArray();
        switch(array.getStrongType()){
            case Int8: {
                byte[] data = ((UBInt8Array) array).getValues();
                retval = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int16: {
                short[] data = ((UBInt16Array) array).getValues();
                retval = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int32: {
                int[] data = ((UBInt32Array) array).getValues();
                retval = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Int64: {
                long[] data = ((UBInt64Array) array).getValues();
                retval = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }

            case Float32: {
                float[] data = ((UBFloat32Array) array).getValues();
                retval = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    retval[i] = data[i];
                }
                break;
            }


            case Float64: {
                retval = ((UBFloat64Array)array).getValues();
                break;
            }

            default:
                throw new RuntimeException("not an float32[] type");
        }

        return retval;
    }

    public String[] asStringArray() {
        String[] retval;

        UBArray array = asArray();
        if(UBArray.ArrayType.String != array.getStrongType()){
            throw new RuntimeException("not a strongly-typed string array");
        }
        return ((UBStringArray)array).getValues();
    }

    public boolean isObject() {
        return getType() == Type.Object;
    }

    public UBObject asObject() {
        return ((UBObject)this);
    }

    @Override
    public int hashCode() {
        int retval = 0;
        switch (getType()) {
            case Null:
                retval = 0;
                break;
            case Bool:
                retval = asBool() ? 1 : 0;
                break;
            case Char:
                retval = asChar();
                break;
            case Int8:
            case Uint8:
            case Int16:
            case Int32:
                retval = asInt();
                break;
            case Int64: {
                long value = asLong();
                retval = (int) (value ^ (value >>> 32));
                break;
            }

            case Float32:
                retval = Float.floatToIntBits(asFloat32());
                break;

            case Float64: {
                long value = Double.doubleToLongBits(asFloat64());
                retval = (int) (value ^ (value >>> 32));
                break;
            }

            case String:
                retval = asString().hashCode();
                break;

        }
        return retval;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof UBValue)) {
            return false;
        }
        return COMPARATOR.compare(this, (UBValue)obj) == 0;
    }

    @Override
    public int compareTo(UBValue value) {
        return COMPARATOR.compare(this, value);
    }

    private static int getCompareType(UBValue value) {
        int retval = 0;
        switch(value.getType()) {
            case Null:
                retval = 0;
                break;

            case Bool:
                retval = 1;
                break;
            case Char:
                retval = 2;
                break;
            case Int8:
            case Uint8:
            case Int16:
            case Int32:
            case Int64:
                retval = 3;
                break;
            case Float32:
            case Float64:
                retval = 4;
                break;
            case String:
                retval = 5;
                break;
            case Array:
                retval = 6;
                break;
            case Object:
                retval = 7;
                break;
        }

        return retval;
    }

    private static int compareBool(boolean a, boolean b){
        return (a == b) ? 0 : (a ? 1 : -1);
    }

    private static int compareChar(char a, char b) {
        return (a == b) ? 0 : (a < b ? -1 : 1);
    }

    private static int compareLong(long a, long b) {
        return (a < b) ? -1 : ((a > b) ? 1 : 0);
    }

    public static final Comparator<UBValue> COMPARATOR = new Comparator<UBValue>() {



        @Override
        public int compare(UBValue a, UBValue b) {
            int retval = getCompareType(a) - getCompareType(b);
            if(retval == 0) {
                switch (getCompareType(a)) {
                    case 1:
                        retval = compareBool(a.asBool(), b.asBool());
                        break;

                    case 2:
                        retval = compareChar(a.asChar(), b.asChar());
                        break;

                    case 3:
                        retval = compareLong(a.asLong(), b.asLong());
                        break;

                    case 4:
                        retval = Double.compare(a.asFloat64(), b.asFloat64());
                        break;

                    case 5:
                        retval = a.asString().compareTo(b.asString());
                        break;

                    case 6:
                        retval = a.asArray().compareTo(b.asArray());
                        break;

                    case 7:
                        retval = a.asObject().compareTo(b.asObject());
                        break;
                }
            }

            return retval;
        }
    };
}
