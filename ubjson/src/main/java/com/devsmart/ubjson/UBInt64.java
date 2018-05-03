package com.devsmart.ubjson;


public final class UBInt64 extends UBValue {

    private static final long serialVersionUID = 9165871024451132472L;
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
