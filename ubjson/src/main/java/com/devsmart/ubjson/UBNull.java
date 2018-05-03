package com.devsmart.ubjson;


public final class UBNull extends UBValue {

    private static final long serialVersionUID = -7091168100810431108L;

    @Override
    public Type getType() {
        return Type.Null;
    }

    @Override
    public String toString() {
        return "null";
    }
}
