package com.devsmart.microdb.ubjson;


public final class UBInt8 extends UBValue {

    private byte mValue;

    UBInt8(long value) {
        mValue = (byte) value;
    }

    @Override
    public Type getType() {
        return Type.Int8;
    }

    public int getInt() {
        return mValue;
    }

    @Override
    public String toString() {
        return Integer.toString(getInt());
    }
}
