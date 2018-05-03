package com.devsmart.ubjson;

import java.util.Arrays;

public class UBInt16Array extends UBArray {

    private static final long serialVersionUID = 341107852354569328L;
    private final short[] mArray;

    UBInt16Array(short[] values) {
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
        return ArrayType.Int16;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createInt(mArray[index]);
    }

    public short[] getValues() {
        return mArray;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
