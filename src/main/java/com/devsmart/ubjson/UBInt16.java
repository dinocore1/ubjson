package com.devsmart.microdb.ubjson;


public final class UBInt16 extends UBValue {

    private short mValue;

    UBInt16(long value) {
        mValue = (short)(0xFFFF & value);
    }

    @Override
    public Type getType() {
        return Type.Int16;
    }

    public int getInt() {
        return mValue;
    }

    @Override
    public String toString() {
        return Integer.toString(getInt());
    }
}
