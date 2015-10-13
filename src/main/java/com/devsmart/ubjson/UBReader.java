package com.devsmart.microdb.ubjson;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


public class UBReader implements Closeable {

    private final InputStream mInputStream;

    public UBReader(InputStream in) {
        mInputStream = in;
    }

    @Override
    public void close() throws IOException {
        mInputStream.close();
    }

    private byte readControl() throws IOException {
        int value = mInputStream.read();
        if(value == -1) {
            throw new IOException("eof");
        } else {
            return (byte)value;
        }
    }

    private byte readInt8() throws IOException {
        return readControl();
    }

    private short readUInt8() throws IOException {
        return (short)(0xFF & readControl());
    }

    private short readInt16() throws IOException {
        return (short)((readControl() & 0xFF) << 8 | (readControl() & 0xFF));
    }

    private int readInt32() throws IOException {
        return ( (readControl() & 0xFF) << 24 | (readControl() & 0xFF) << 16
                | (readControl() & 0xFF) << 8 | (readControl() & 0xFF) );
    }

    private long readInt(byte control) throws IOException {
        long value;
        switch (control) {
            case UBValue.MARKER_INT8:
                value = readInt8();
                break;

            case UBValue.MARKER_UINT8:
                value = readUInt8();
                break;

            case UBValue.MARKER_INT16:
                value = readInt16();
                break;

            case UBValue.MARKER_INT32:
                value = readInt32();
                break;

            case UBValue.MARKER_INT64:
                value = ((long)readControl() & 0xFF) << 56 | ((long)readControl() & 0xFF) << 48
                        | ((long)readControl() & 0xFF) << 40 | ((long)readControl() & 0xFF) << 32
                        | ((long)readControl() & 0xFF) << 24 | ((long)readControl() & 0xFF) << 16
                        | ((long)readControl() & 0xFF) << 8 | ((long)readControl() & 0xFF);
                break;

            default:
                throw new IOException("not an int type");

        }

        return value;
    }

    private char readChar() throws IOException {
        char value = (char) readControl();
        return value;
    }

    private float readFloat32() throws IOException {
        int intvalue = (readControl() & 0xFF) << 24 | (readControl() & 0xFF) << 16
                | (readControl() & 0xFF) << 8 | (readControl() & 0xFF);

        float value = Float.intBitsToFloat(intvalue);
        return value;
    }

    private double readFloat64() throws IOException {
        long intvalue = ((long)readControl() & 0xFF) << 56 | ((long)readControl() & 0xFF) << 48
                | ((long)readControl() & 0xFF) << 40 | ((long)readControl() & 0xFF) << 32
                | ((long)readControl() & 0xFF) << 24 | ((long)readControl() & 0xFF) << 16
                | ((long)readControl() & 0xFF) << 8 | ((long)readControl() & 0xFF);


        double value = Double.longBitsToDouble(intvalue);
        return value;
    }

    private byte[] readString(byte control) throws IOException {
        int size = (int) readInt(control);
        byte[] value = new byte[size];

        int bytesRead = mInputStream.read(value, 0, size);
        if(bytesRead != size) {
            throw new IOException("eof reached");
        }
        return value;
    }

    private byte[] readOptimizedArrayInt8(int size) throws IOException {
        byte[] data = new byte[size];
        for(int i=0;i<size;i++){
            data[i] = readInt8();
        }
        return data;
    }

    private short[] readOptimizedArrayInt16(int size) throws IOException {
        short[] data = new short[size];
        for(int i=0;i<size;i++){
            data[i] = readInt16();
        }
        return data;
    }

    private int[] readOptimizedArrayInt32(int size) throws IOException {
        int[] data = new int[size];
        for(int i=0;i<size;i++){
            data[i] = readInt32();
        }
        return data;
    }

    private float[] readOptimizedArrayFloat32(int size) throws IOException {
        float[] data = new float[size];
        for(int i=0;i<size;i++){
            data[i] = readFloat32();
        }
        return data;
    }

    private double[] readOptimizedArrayFloat64(int size) throws IOException {
        double[] data = new double[size];
        for(int i=0;i<size;i++){
            data[i] = readFloat64();
        }
        return data;
    }

    private UBValue[] readOptimizedArray(int size, byte type) throws IOException {
        UBValue[] retval = new UBValue[size];
        for(int i=0;i<size;i++) {
            retval[i] = readValue(type);
        }
        return retval;

    }

    private UBValue[] readOptimizedArray(int size) throws IOException {
        UBValue[] retval = new UBValue[size];
        for(int i=0;i<size;i++) {
            byte type = readControl();
            retval[i] = readValue(type);
        }
        return retval;
    }

    private UBArray readArray() throws IOException {
        byte control, type;
        int size;

        control = readControl();
        if(control == UBValue.MARKER_OPTIMIZED_TYPE) {
            type = readControl();

            if(readControl() != UBValue.MARKER_OPTIMIZED_SIZE) {
                throw new IOException("optimized size missing");
            }
            size = (int)readInt(readControl());

            switch (type) {

                case UBValue.MARKER_INT8:
                    return UBValueFactory.createArray(readOptimizedArrayInt8(size));

                case UBValue.MARKER_INT16:
                    return UBValueFactory.createArray(readOptimizedArrayInt16(size));

                case UBValue.MARKER_INT32:
                    return UBValueFactory.createArray(readOptimizedArrayInt32(size));

                case UBValue.MARKER_FLOAT32:
                    return UBValueFactory.createArray(readOptimizedArrayFloat32(size));

                case UBValue.MARKER_FLOAT64:
                    return UBValueFactory.createArray(readOptimizedArrayFloat64(size));

                default:
                    return UBValueFactory.createArray(readOptimizedArray(size, type));

            }


        } else if(control == UBValue.MARKER_OPTIMIZED_SIZE) {
            size = (int)readInt(readControl());
            return UBValueFactory.createArray(readOptimizedArray(size));
        } else {
            ArrayList<UBValue> data = new ArrayList<UBValue>();

            while(control != UBValue.MARKER_ARRAY_END) {
                data.add(readValue(control));
                control = readControl();
            }

            return UBValueFactory.createArray(data.toArray(new UBValue[data.size()]));
        }

    }

    private UBValue readObj() throws IOException {
        byte control, type;
        int size;
        TreeMap<String, UBValue> obj = new TreeMap<String, UBValue>();

        control = readControl();
        if(control == UBValue.MARKER_OPTIMIZED_TYPE) {
            type = readControl();

            if(readControl() != UBValue.MARKER_OPTIMIZED_SIZE) {
                throw new IOException("optimized size missing");
            }
            size = (int)readInt(readControl());

            for(int i=0;i<size;i++) {
                String key = new String(readString(readControl()), UBString.UTF_8);
                UBValue value = readValue(type);

                obj.put(key, value);
            }

        } else if(control == UBValue.MARKER_OPTIMIZED_SIZE) {
            size = (int)readInt(readControl());

            for(int i=0;i<size;i++) {
                String key = new String(readString(readControl()), UBString.UTF_8);
                UBValue value = readValue(readControl());

                obj.put(key, value);
            }

        } else {

            while(control != UBValue.MARKER_OBJ_END) {
                String key = new String(readString(control), UBString.UTF_8);
                control = readControl();
                UBValue value = readValue(control);

                obj.put(key, value);

                control = readControl();
            }
        }

        return UBValueFactory.createObject(obj);
    }

    private UBValue readValue(byte control) throws IOException {
        UBValue retval = null;
        switch(control) {
            case UBValue.MARKER_NULL:
                retval = UBValueFactory.createNull();
                break;

            case UBValue.MARKER_TRUE:
                retval = UBValueFactory.createBool(true);
                break;

            case UBValue.MARKER_FALSE:
                retval = UBValueFactory.createBool(false);
                break;

            case UBValue.MARKER_CHAR:
                retval = UBValueFactory.createChar(readChar());
                break;

            case UBValue.MARKER_INT8:
            case UBValue.MARKER_UINT8:
            case UBValue.MARKER_INT16:
            case UBValue.MARKER_INT32:
            case UBValue.MARKER_INT64:
                retval = UBValueFactory.createInt(readInt(control));
                break;

            case UBValue.MARKER_FLOAT32:
                retval = UBValueFactory.createFloat32(readFloat32());
                break;

            case UBValue.MARKER_FLOAT64:
                retval = UBValueFactory.createFloat64(readFloat64());
                break;

            case UBValue.MARKER_STRING:
                retval = UBValueFactory.createString(readString(readControl()));
                break;

            case UBValue.MARKER_ARRAY_START:
                retval = readArray();
                break;

            case UBValue.MARKER_OBJ_START:
                retval = readObj();
                break;
        }

        return retval;
    }

    public UBValue read() throws IOException {
        byte control = readControl();
        return readValue(control);
    }
}
