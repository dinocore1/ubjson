package com.devsmart.ubjson;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UBFactory {

    @Test
    public void testInt() {

        UBValue value;

        value = UBValueFactory.createInt(-128);
        assertEquals(UBValue.Type.Int8, value.getType());
        assertEquals(-128, value.asInt());

        value = UBValueFactory.createInt(127);
        assertTrue(UBValue.Type.Int8 == value.getType() || UBValue.Type.Uint8 == value.getType());
        assertEquals(127, value.asInt());

        value = UBValueFactory.createInt(255);
        assertEquals(UBValue.Type.Uint8, value.getType());
        assertEquals(255, value.asInt());

        value = UBValueFactory.createInt(257);
        assertEquals(UBValue.Type.Int16, value.getType());
        assertEquals(257, value.asInt());

        value = UBValueFactory.createInt(-32768);
        assertEquals(UBValue.Type.Int16, value.getType());
        assertEquals(-32768, value.asInt());

        value = UBValueFactory.createInt(2147483647);
        assertEquals(UBValue.Type.Int32, value.getType());
        assertEquals(2147483647, value.asInt());

        value = UBValueFactory.createInt(Long.parseLong("9223372036854775807"));
        assertEquals(UBValue.Type.Int64, value.getType());
        assertEquals(Long.parseLong("9223372036854775807"), value.asLong());

    }

    @Test
    public void testGenericBool() {
        UBValue v = UBValueFactory.createValue(true);
        assertTrue(v.isBool());
        assertEquals(true, v.asBool());
    }

    @Test
    public void testGenericInt() {
        UBValue v = UBValueFactory.createValue(5);
        assertTrue(v.isNumber());
        assertTrue(v.isInteger());
        assertEquals(5, v.asInt());
    }

    @Test
    public void testGenericFloat() {
        UBValue v = UBValueFactory.createValue(3.14);
        assertTrue(v.isNumber());
        assertFalse(v.isInteger());
        assertEquals(3.14f, v.asFloat32(), 0.0001);
    }

    @Test
    public void testGenericString() {
        UBValue v = UBValueFactory.createValue("hello");
        assertFalse(v.isNumber());
        assertFalse(v.isInteger());
        assertTrue(v.isString());
        assertEquals("hello", v.asString());
    }

    @Test
    public void testGenericArray() {

        UBValue v = UBValueFactory.createValue(
                Arrays.asList(new String[]{"hello", "world"})
        );
        assertFalse(v.isNumber());
        assertFalse(v.isInteger());
        assertFalse(v.isString());
        assertTrue(v.isArray());

        UBArray array = v.asArray();
        assertEquals(2, array.size());
        assertEquals("hello", array.get(0).asString());

    }

    @Test
    public void testGenericMap() {

        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("hello", "world");
        myMap.put("pi", 3.14);

        UBValue v = UBValueFactory.createValue(myMap);
        assertFalse(v.isArray());
        assertTrue(v.isObject());

        UBObject obj = v.asObject();
        assertEquals(2, obj.size());
        assertEquals("world", obj.get("hello").asString());
        assertEquals(3.14f, obj.get("pi").asFloat32(), 0.0001);
    }

}
