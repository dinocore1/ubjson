package com.devsmart.microdb.ubjson;


public final class UBBool extends UBValue {

    private boolean mValue;

    UBBool(boolean value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Bool;
    }

    public boolean getBool() {
        return mValue;
    }
}
