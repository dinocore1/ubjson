package com.devsmart.microdb.ubjson;


public class UBArray extends UBValue {

    public enum ArrayType {
        Generic,
        Int8,
        Int16,
        Int32,
        Float32,
        Float64
    }

    private UBValue[] mValue;

    UBArray() {

    }

    UBArray(UBValue[] value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Array;
    }

    public boolean isStronglyTyped() {
        return false;
    }

    public ArrayType getStrongType() {
        return ArrayType.Generic;
    }

    public int size() {
        return mValue.length;
    }

    public UBValue get(int index) {
        return mValue[index];
    }

    @Override
    public int hashCode() {
        int retval = 0;
        final int size = size();
        for(int i=0;i<size;i++) {
            retval ^= get(i).hashCode();
        }
        return retval;
    }

    @Override
    public int compareTo(UBValue o) {
        final UBArray other = (UBArray)o;

        final int thisSize = size();
        final int otherSize = other.size();

        int minSize = Math.min(thisSize, otherSize);
        for(int i=0;i<minSize;i++){
            int retval = UBValue.COMPARATOR.compare(get(i), other.get(i));
            if(retval != 0) {
                return retval;
            }
        }

        if(thisSize == otherSize) {
            return 0;
        } else if(thisSize < otherSize) {
            return -1;
        } else {
            return 1;
        }

    }
}
