package com.devsmart.ubjson;


public final class UBBool extends UBValue {

    private static final long serialVersionUID = -6877924058910303289L;
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
