package com.devsmart.microdb.ubjson;


public final class UBUInt8 extends UBValue {

    private short mValue;

    UBUInt8(long value) {
        mValue = (short)(0xFF & value);
    }

    @Override
    public Type getType() {
        return Type.Uint8;
    }

    int getInt() {
        return mValue;
    }

    @Override
    public String toString() {
        return Integer.toString(getInt());
    }
}
