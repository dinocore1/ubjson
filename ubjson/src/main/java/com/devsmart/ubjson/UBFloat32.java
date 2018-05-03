package com.devsmart.ubjson;


public final class UBFloat32 extends UBValue {

    private static final long serialVersionUID = 7488304650328539811L;
    private float mValue;

    UBFloat32(float value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Float32;
    }

    public float getFloat() {
        return mValue;
    }

    public double getDouble() {
        return mValue;
    }

    @Override
    public String toString() {
        return Float.toString(getFloat());
    }
}
