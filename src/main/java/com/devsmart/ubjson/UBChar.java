package com.devsmart.microdb.ubjson;


public final class UBChar extends UBValue {

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
