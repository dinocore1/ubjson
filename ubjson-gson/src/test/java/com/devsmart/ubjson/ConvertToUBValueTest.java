package com.devsmart.ubjson;


import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertToUBValueTest {


    @Test
    public void testConvertNull() {
        UBValue value = GsonUtil.toUBValue(JsonNull.INSTANCE);
        assertNotNull(value);
        assertTrue(value.isNull());
    }

    @Test
    public void testConvertString() {
        final String strValue = "hello world";
        UBValue value = GsonUtil.toUBValue(new JsonPrimitive(strValue));
        assertNotNull(value);
        assertTrue(value.isString());
        assertEquals(strValue, value.asString());
    }

    @Test
    public void testConvertBool() {
        boolean boolValue = true;
        UBValue value = GsonUtil.toUBValue(new JsonPrimitive(boolValue));
        assertNotNull(value);
        assertTrue(value.isBool());
        assertEquals(boolValue, value.asBool());

        boolValue = false;
        value = GsonUtil.toUBValue(new JsonPrimitive(boolValue));
        assertNotNull(value);
        assertTrue(value.isBool());
        assertEquals(boolValue, value.asBool());
    }

    @Test
    public void testConvertInt() {
        final int intValue = 364;
        UBValue value = GsonUtil.toUBValue(new JsonPrimitive(intValue));
        assertNotNull(value);
        assertTrue(value.isNumber());
        assertTrue(value.isInteger());
        assertEquals(intValue, value.asInt());
    }
}
