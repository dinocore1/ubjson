package com.devsmart.ubjson;


import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class UBValueTest {

    @Test
    public void testCompare() {
        UBValue v1 = UBValueFactory.createString("aab");
        UBValue v2 = UBValueFactory.createString("aaz");
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createBool(false);
        v2 = UBValueFactory.createBool(false);
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createBool(false);
        v2 = UBValueFactory.createBool(true);
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createBool(true);
        v2 = UBValueFactory.createBool(true);
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createChar('a');
        v2 = UBValueFactory.createChar('a');
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createChar('a');
        v2 = UBValueFactory.createChar('b');
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createChar('a');
        v2 = UBValueFactory.createString("a");
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createBool(false);
        v2 = UBValueFactory.createInt(0);
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createString("aab");
        v2 = UBValueFactory.createString("aab");
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createString("aab");
        v2 = UBValueFactory.createString("aaba");
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createInt(3);
        v2 = UBValueFactory.createInt(3);
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createInt(3);
        v2 = UBValueFactory.createString("3");
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createArray(UBValueFactory.createString("aaa"));
        v2 = UBValueFactory.createArray(UBValueFactory.createString("aaa"));
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createArray(UBValueFactory.createString("aaa"));
        v2 = UBValueFactory.createArray(UBValueFactory.createString("aaa"), UBValueFactory.createInt(4));
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);

        v1 = UBValueFactory.createArray(UBValueFactory.createString("aaa"), UBValueFactory.createInt(4));
        v2 = UBValueFactory.createArray(new UBValue[] {UBValueFactory.createString("aaa"), UBValueFactory.createInt(4)});
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);


        v1 = UBValueFactory.createArray(new float[] {0, 1});
        v2 = UBValueFactory.createArray(new float[] {0, 1});
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) == 0);

        v1 = UBValueFactory.createArray(new float[] {0, 1});
        v2 = UBValueFactory.createArray(new float[] {0, 1, 0});
        assertTrue(UBValue.COMPARATOR.compare(v1, v2) < 0);


    }

    @Test
    public void testHashCode() {
        UBValue v;

        v = UBValueFactory.createObject();
        final int emptyObjHash = v.hashCode();

        v = UBValueFactory.createObject();
        assertEquals(emptyObjHash, v.hashCode());

        UBObject a = v.asObject();
        a.put("hello", UBValueFactory.createString("world"));
        a.put("a", UBValueFactory.createInt(8));

        assertNotEquals(emptyObjHash, a.hashCode());

        UBObject b = UBValueFactory.createObject();
        b.put("a", UBValueFactory.createInt(8));
        b.put("hello", UBValueFactory.createString("world"));

        assertEquals(a.hashCode(), b.hashCode());

        assertTrue(a.equals(b));
    }

    @Test
    public void testHashEquals() {
        HashSet<UBValue> set = new HashSet<UBValue>();

        set.add(UBValueFactory.createInt(0));
        assertTrue(set.contains(UBValueFactory.createInt(0)));
        assertFalse(set.contains(UBValueFactory.createBool(false)));
        assertFalse(set.contains(UBValueFactory.createArray(UBValueFactory.createInt(0))));

        set.add(UBValueFactory.createInt(0));
        assertEquals(1, set.size());

        set.add(UBValueFactory.createFloat32(0f));
        assertEquals(2, set.size());

    }

    @Test
    public void testStringArray() {

        String[] value = new String[]{"a", "b", "c"};
        UBArray ubarray = UBValueFactory.createArray(value);
        assertNotNull(ubarray);
        assertTrue(ubarray.isArray());
        assertTrue(ubarray.isStronglyTyped());
        assertEquals(UBArray.ArrayType.String, ubarray.getStrongType());
        assertEquals(3, ubarray.size());
        assertEquals("a", ubarray.get(0).asString());
        assertEquals("b", ubarray.get(1).asString());
        assertEquals("c", ubarray.get(2).asString());

        String[] output = ubarray.asStringArray();
        assertNotNull(output);
        assertEquals(3, output.length);
        assertEquals("a", output[0]);
        assertEquals("b", output[1]);
        assertEquals("c", output[2]);

    }

    @Test
    public void testFloat32AsInt() {
        UBFloat32 pi = UBValueFactory.createFloat32(3.14f);
        assertEquals(3, pi.asInt());
    }

    @Test
    public void testFloat64AsInt() {
        UBFloat64 pi = UBValueFactory.createFloat64(3.14);
        assertEquals(3, pi.asInt());
    }

    @Test
    public void testIntAsFloat() {
        UBValue value = UBValueFactory.createInt(3);
        assertEquals(3.0f, value.asFloat32(), 0.000001);
    }
}
