package com.devsmart.ubjson;


import java.util.Arrays;

public class UBInt32Array extends UBArray {

    private static final long serialVersionUID = -5301755027222929313L;
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

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
