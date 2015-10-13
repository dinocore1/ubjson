package com.devsmart.microdb.ubjson;


import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class UBWriter implements Closeable {

    private final OutputStream mOutputStream;

    public UBWriter(OutputStream out) {
        mOutputStream = out;
    }

    @Override
    public void close() throws IOException {
        mOutputStream.close();
    }

    public void writeNull() throws IOException {
        mOutputStream.write(UBValue.MARKER_NULL);
    }

    public void writeBool(boolean value) throws IOException {
        if(value) {
            mOutputStream.write(UBValue.MARKER_TRUE);
        } else {
            mOutputStream.write(UBValue.MARKER_FALSE);
        }
    }

    public void writeChar(char value) throws IOException {
        mOutputStream.write(UBValue.MARKER_CHAR);
        mOutputStream.write(value);
    }

    public void writeInt8(byte value) throws IOException {
        mOutputStream.write(UBValue.MARKER_INT8);
        mOutputStream.write(value);
    }

    public void writeUInt8(short value) throws IOException {
        mOutputStream.write(UBValue.MARKER_UINT8);
        mOutputStream.write(0xFF & value);
    }

    public void writeInt16(short value) throws IOException {
        mOutputStream.write(UBValue.MARKER_INT16);
        mOutputStream.write(value >> 8);
        mOutputStream.write(value);
    }

    public void writeInt32(int value) throws IOException {
        mOutputStream.write(UBValue.MARKER_INT32);
        mOutputStream.write((value >> 24));
        mOutputStream.write((value >> 16));
        mOutputStream.write((value >> 8));
        mOutputStream.write(value);
    }

    public void writeInt64(long value) throws IOException {
        mOutputStream.write(UBValue.MARKER_INT64);
        mOutputStream.write((int) ((value >> 56)));
        mOutputStream.write((int) ((value >> 48)));
        mOutputStream.write((int) ((value >> 40)));
        mOutputStream.write((int) ((value >> 32)));
        mOutputStream.write((int) ((value >> 24)));
        mOutputStream.write((int) ((value >> 16)));
        mOutputStream.write((int) ((value >> 8)));
        mOutputStream.write((int) (value));
    }

    public void writeInt(long value) throws IOException {
        if(UBValueFactory.inRange(value, 0, 255)) {
            writeUInt8((byte)value);
        } else if(UBValueFactory.inRange(value, -128, 127)) {
            writeInt8((byte)value);
        } else if(UBValueFactory.inRange(value, -32768, 32767)) {
            writeInt16((short)value);
        } else if(UBValueFactory.inRange(value, -2147483648, 2147483647)) {
            writeInt32((int)value);
        } else {
            writeInt64(value);
        }
    }

    public void writeFloat32(float value) throws IOException {
        mOutputStream.write(UBValue.MARKER_FLOAT32);

        int intValue = Float.floatToIntBits(value);
        mOutputStream.write((intValue >> 24));
        mOutputStream.write((intValue >> 16));
        mOutputStream.write((intValue >> 8));
        mOutputStream.write(intValue);
    }

    public void writeFloat64(double value) throws IOException {
        mOutputStream.write(UBValue.MARKER_FLOAT64);

        long intValue = Double.doubleToLongBits(value);
        mOutputStream.write((int) ((intValue >> 56)));
        mOutputStream.write((int) ((intValue >> 48)));
        mOutputStream.write((int) ((intValue >> 40)));
        mOutputStream.write((int) ((intValue >> 32)));
        mOutputStream.write((int) ((intValue >> 24)));
        mOutputStream.write((int) ((intValue >> 16)));
        mOutputStream.write((int) ((intValue >> 8)));
        mOutputStream.write((int) (intValue));
    }

    public void writeInt8Array(byte[] value) throws IOException {
        mOutputStream.write(UBValue.MARKER_ARRAY_START);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_TYPE);
        mOutputStream.write(UBValue.MARKER_INT8);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_SIZE);
        writeInt(value.length);

        for(int i=0;i<value.length;i++){
            writeInt8(value[i]);
        }
    }

    public void writeInt16Array(short[] value) throws IOException {
        mOutputStream.write(UBValue.MARKER_ARRAY_START);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_TYPE);
        mOutputStream.write(UBValue.MARKER_INT16);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_SIZE);
        writeInt(value.length);

        for(int i=0;i<value.length;i++){
            writeInt16(value[i]);
        }
    }

    public void writeInt32Array(int[] value) throws IOException {
        mOutputStream.write(UBValue.MARKER_ARRAY_START);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_TYPE);
        mOutputStream.write(UBValue.MARKER_INT32);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_SIZE);
        writeInt(value.length);

        for(int i=0;i<value.length;i++){
            writeInt32(value[i]);
        }
    }

    public void writeFloat32Array(float[] value) throws IOException {
        mOutputStream.write(UBValue.MARKER_ARRAY_START);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_TYPE);
        mOutputStream.write(UBValue.MARKER_FLOAT32);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_SIZE);
        writeInt(value.length);

        for(int i=0;i<value.length;i++){
            writeFloat32(value[i]);
        }
    }

    public void writeFloat64Array(double[] value) throws IOException {
        mOutputStream.write(UBValue.MARKER_ARRAY_START);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_TYPE);
        mOutputStream.write(UBValue.MARKER_FLOAT64);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_SIZE);
        writeInt(value.length);

        for(int i=0;i<value.length;i++){
            writeFloat64(value[i]);
        }
    }

    public void writeGenericeArray(UBArray value) throws IOException {
        mOutputStream.write(UBValue.MARKER_ARRAY_START);
        mOutputStream.write(UBValue.MARKER_OPTIMIZED_SIZE);

        final int size = value.size();
        writeInt(size);

        for(int i=0;i<size;i++){
            write(value.get(i));
        }
    }

    public void writeString(UBString string) throws IOException {
        mOutputStream.write(UBValue.MARKER_STRING);
        byte[] data = string.getData();
        writeDataArray(data);
    }

    public void writeDataArray(byte[] data) throws IOException {
        writeInt(data.length);
        mOutputStream.write(data);
    }

    public void writeArray(UBArray value) throws IOException {
        switch(value.getStrongType()) {

            case Int8:
                writeInt8Array( ((UBInt8Array)value).getValues() );
                break;

            case Int16:
                writeInt16Array(((UBInt16Array) value).getValues() );
                break;

            case Int32:
                writeInt32Array( ((UBInt32Array)value).getValues() );
                break;

            case Float32:
                writeFloat32Array(((UBFloat32Array)value).getValues());
                break;

            case Float64:
                writeFloat64Array(((UBFloat64Array)value).getValues());
                break;

            default:
                writeGenericeArray( value );
                break;

        }
    }

    public void writeObject(UBObject object) throws IOException {
        mOutputStream.write(UBValue.MARKER_OBJ_START);
        for(Map.Entry<String, UBValue> entry : object.entrySet()) {
            writeDataArray(entry.getKey().getBytes(UBString.UTF_8));
            write(entry.getValue());
        }
        mOutputStream.write(UBValue.MARKER_OBJ_END);
    }

    public void write(UBValue value) throws IOException {
        switch (value.getType()) {
            case Null:
                writeNull();
                break;

            case Bool:
                writeBool(value.asBool());
                break;

            case Char:
                writeChar(value.asChar());
                break;

            case Int8:
                writeInt8((byte)value.asInt());
                break;

            case Uint8:
                writeUInt8((short) value.asInt());
                break;

            case Int16:
                writeInt16((short) value.asInt());
                break;

            case Int32:
                writeInt32((int) value.asInt());
                break;

            case Int64:
                writeInt64(value.asInt());
                break;

            case Float32:
                writeFloat32(value.asFloat32());
                break;

            case Float64:
                writeFloat64(value.asFloat64());
                break;

            case String:
                writeString((UBString)value);
                break;

            case Array:
                writeArray(value.asArray());
                break;

            case Object:
                writeObject(value.asObject());
                break;

        }
    }
}
