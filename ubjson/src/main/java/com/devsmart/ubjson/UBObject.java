package com.devsmart.ubjson;


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class UBObject extends UBValue implements Map<String, UBValue> {

    private static final long serialVersionUID = 6569912557379906502L;
    /**
     * a tree map is used here to ensure that the keys are always sorted. This
     * is useful when calculating {@code hashCode()}.
     */
    private TreeMap<String, UBValue> mValue;

    UBObject() {
        this(new TreeMap<String, UBValue>());
    }

    UBObject(TreeMap<String, UBValue> value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Object;
    }

    @Override
    public boolean isEmpty() {
        return mValue.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mValue.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mValue.containsValue(value);
    }

    @Override
    public UBValue get(Object key) {
        return mValue.get(key);
    }

    @Override
    public UBValue put(String key, UBValue value) {
        return mValue.put(key, value);
    }

    @Override
    public UBValue remove(Object key) {
        return mValue.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends UBValue> m) {
        mValue.putAll(m);
    }

    @Override
    public void clear() {
        mValue.clear();
    }

    @Override
    public Set<String> keySet() {
        return mValue.keySet();
    }

    @Override
    public Collection<UBValue> values() {
        return mValue.values();
    }

    @Override
    public Set<Entry<String, UBValue>> entrySet() {
        return mValue.entrySet();
    }

    @Override
    public int size() {
        return mValue.size();
    }

    @Override
    public int hashCode() {
        int retval = 0;
        for(Map.Entry<String, UBValue> entry : mValue.entrySet()) {
            retval ^= entry.getKey().hashCode() + entry.getValue().hashCode();
        }
        return retval;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof UBObject)) {
            return false;
        }

        UBObject b = (UBObject) obj;
        return mValue.equals(b.mValue);
    }
}
