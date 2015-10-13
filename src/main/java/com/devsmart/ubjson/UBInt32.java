package com.devsmart.microdb.ubjson;


public final class UBInt32 extends UBValue {

    private int mValue;

    UBInt32(long value) {
        mValue = (int) value;
    }

    @Override
    public Type getType() {
        return Type.Int32;
    }

    public int getInt() {
        return mValue;
    }

    @Override
    public String toString() {
        return Integer.toString(getInt());
    }
}
