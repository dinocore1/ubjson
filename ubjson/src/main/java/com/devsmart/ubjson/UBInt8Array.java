package com.devsmart.ubjson;


import java.util.Arrays;

public final class UBInt8Array extends UBArray {
    private static final long serialVersionUID = 1503819741239478254L;
    private final byte[] mArray;

    UBInt8Array(byte[] values) {
        mArray = values;
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
        return ArrayType.Int8;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createInt(mArray[index]);
    }

    public byte[] getValues() {
        return mArray;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
