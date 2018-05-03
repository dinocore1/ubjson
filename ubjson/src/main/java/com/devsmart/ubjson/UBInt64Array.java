package com.devsmart.ubjson;


import java.util.Arrays;

public class UBInt64Array extends UBArray {

    private static final long serialVersionUID = -460593990460020606L;
    private final long[] mArray;

    UBInt64Array(long[] value) {
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
        return ArrayType.Int64;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createInt(mArray[index]);
    }

    public long[] getValues() {
        return mArray;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
