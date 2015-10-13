package com.devsmart.microdb.ubjson;


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class UBObject extends UBValue implements Map<String, UBValue> {

    private TreeMap<String, UBValue> mValue;

    public UBObject() {
        mValue = new TreeMap<String, UBValue>();
    }

    public UBObject(TreeMap<String, UBValue> value) {
        mValue = value;
    }

    @Override
    public Type getType() {
        return Type.Object;
    }

    /*
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(Map.Entry<String, UBValue> entry : mValue.entrySet()) {
            builder.append(entry.getKey() + " => " + entry.getValue().toString());
            builder.append('\n');
        }

        return builder.toString();
    }
    */

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
}
