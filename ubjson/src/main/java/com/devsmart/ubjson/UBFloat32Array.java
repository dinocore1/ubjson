package com.devsmart.ubjson;


import java.util.Arrays;

public final class UBFloat32Array extends UBArray {

    private static final long serialVersionUID = 8818783817337699100L;
    private final float[] mArray;

    UBFloat32Array(float[] values) {
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
        return ArrayType.Float32;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createFloat32(mArray[index]);
    }

    public float[] getValues() {
        return mArray;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mArray);
    }
}
