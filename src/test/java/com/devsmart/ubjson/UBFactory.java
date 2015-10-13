package com.devsmart.microdb.ubjson;


import org.junit.Test;

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

}
