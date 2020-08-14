package com.devsmart.ubjson;


import java.util.Arrays;

public final class UBInt8Array extends UBArray {
    private static final long serialVersionUID = 1503819741239478254L;
    private final byte[] mArray;
    private final boolean unsigned;
    private final ArrayType type;

    UBInt8Array(byte[] values, boolean unsigned) {
        mArray = values;
        this.unsigned = unsigned;
        this.type = unsigned ? ArrayType.UInt8 : ArrayType.Int8;
    }

    @Override
    public Type getType() {
        return Type.Array;
    }

    @Override
    public boolean isStronglyTyped() {
        return true;
    }

    public ArrayType getStrongType() {
        return type;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return unsigned ? UBValueFactory.createInt(mArray[index] & 0xFF) : UBValueFactory.createInt(mArray[index]);
    }

    public byte[] getValues() {
        return mArray;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
