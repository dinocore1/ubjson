package com.devsmart.microdb.ubjson;


public final class UBFloat32Array extends UBArray {

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
}
