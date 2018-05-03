package com.devsmart.ubjson;


public final class UBChar extends UBValue {

    private static final long serialVersionUID = -4380200057155050488L;
    private char mValue;

    UBChar(char value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Char;
    }

    public char getChar() {
        return mValue;
    }
}
