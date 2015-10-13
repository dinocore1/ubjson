package com.devsmart.microdb.ubjson;


public final class UBInt64 extends UBValue {

    private long mValue;

    UBInt64(long value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Int64;
    }

    public long getInt() {
        return mValue;
    }

    @Override
    public String toString() {
        return Long.toString(getInt());
    }
}
