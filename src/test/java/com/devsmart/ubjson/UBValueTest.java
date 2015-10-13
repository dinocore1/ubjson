package com.devsmart.microdb.ubjson;


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
}
