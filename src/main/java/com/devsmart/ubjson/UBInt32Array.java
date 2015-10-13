package com.devsmart.microdb.ubjson;


public class UBInt32Array extends UBArray {

    private final int[] mArray;

    UBInt32Array(int[] value) {
        mArray = value;
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
        return ArrayType.Int32;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createInt(mArray[index]);
    }

    public int[] getValues() {
        return mArray;
    }
}
