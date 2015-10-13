package com.devsmart.microdb.ubjson;


public final class UBInt8Array extends UBArray {
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
}
