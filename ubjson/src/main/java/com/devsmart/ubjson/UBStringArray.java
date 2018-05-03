package com.devsmart.ubjson;


import java.util.Arrays;

public class UBStringArray extends UBArray {

    private static final long serialVersionUID = 9049462290900317723L;
    private final String[] mArray;

    UBStringArray(String[] value) {
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
        return ArrayType.String;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createStringOrNull(mArray[index]);
    }

    public String[] getValues() {
        return mArray;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
