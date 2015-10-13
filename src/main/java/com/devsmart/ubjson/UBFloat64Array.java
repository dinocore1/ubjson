package com.devsmart.microdb.ubjson;


public class UBFloat64Array extends UBArray {

    private final double[] mArray;

    UBFloat64Array(double[] values) {
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
        return ArrayType.Float64;
    }

    @Override
    public int size() {
        return mArray.length;
    }

    @Override
    public UBValue get(int index) {
        return UBValueFactory.createFloat64(mArray[index]);
    }

    public double[] getValues() {
        return mArray;
    }
}
