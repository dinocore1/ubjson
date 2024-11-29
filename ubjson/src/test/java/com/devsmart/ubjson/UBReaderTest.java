package com.devsmart.ubjson;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.Assert.*;

public class UBReaderTest {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    @Test
    public void readNull() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'Z'};
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNull());
    }

    @Test
    public void readBool() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'T'};
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isBool());
        assertEquals(true, value.asBool());

        data = new byte[] {'F'};
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isBool());
        assertEquals(false, value.asBool());
    }

    @Test
    public void readChar() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'C', 'A'};
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isChar());
        assertEquals('A', value.asChar());

        data = new byte[] {'C', 't'};
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isChar());
        assertEquals('t', value.asChar());
    }

    @Test
    public void readInt() throws Exception {

        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'U', (byte)0xff };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNumber());
        assertEquals(255, value.asInt());

        data = new byte[] {'i', (byte)0xff };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNumber());
        assertEquals(-1, value.asInt());

        data = new byte[] {'i', (byte)0xf4 };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNumber());
        assertEquals(-12, value.asInt());

        data = new byte[] {'I', (byte)0x74, (byte)0x39 };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNumber());
        assertEquals(29753, value.asInt());

    }

    @Test
    public void readInt64() throws Exception {
        byte[] data = new byte[]{ 'L', (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x53, (byte)0x2A, (byte)0x85, (byte)0xFB, (byte)0x28 };
        UBReader reader = new UBReader(new ByteArrayInputStream(data));
        UBValue value = reader.read();
        assertTrue(value.isNumber());

        assertEquals((long)Long.valueOf("1456707337000"), value.asLong());
    }

    @Test
    public void readFloat32() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'d', (byte)0x40, (byte)0x48, (byte)0xf5, (byte)0xc3 };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNumber());
        assertEquals(3.14f, value.asFloat32(), 0.000001);
    }

    @Test
    public void reatFloat64() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'D', (byte)0x40, (byte)0x09, (byte)0x1E, (byte)0xB8,
                (byte)0x51, (byte)0xEB, (byte)0x85, (byte)0x1F };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isNumber());
        assertEquals(3.14, value.asFloat64(), 0.000001);
    }

    @Test
    public void readArray() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'[', 'i', (byte)0xff, 'U', (byte)0xff, ']' };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isArray());
        UBArray array = value.asArray();
        assertEquals(2, array.size());
        assertTrue(array.get(0).isNumber());
        assertEquals(-1, array.get(0).asInt());
        assertTrue(array.get(1).isNumber());
        assertEquals(255, array.get(1).asInt());

    }

    @Test
    public void readOptimizedArray() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'[', '#', 'U', (byte)2, 'i', (byte)0xff, 'U', (byte)0xff };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isArray());
        UBArray array = value.asArray();
        assertEquals(2, array.size());
        assertTrue(array.get(0).isNumber());
        assertEquals(-1, array.get(0).asInt());
        assertTrue(array.get(1).isNumber());
        assertEquals(255, array.get(1).asInt());

    }

    @Test
    public void readOptimizedArray2() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'[', '$', 'i', '#', 'U', (byte)2, (byte)0xff, (byte)0x01 };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isArray());
        UBArray array = value.asArray();
        assertEquals(2, array.size());
        assertTrue(array.get(0).isNumber());
        assertEquals(-1, array.get(0).asInt());
        assertTrue(array.get(1).isNumber());
        assertEquals(1, array.get(1).asInt());

    }

    @Test
    public void readOptimizedUInt8Array() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'[', '$', 'U', '#', 'U', (byte)2, (byte)0xff, (byte)0x01 };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isArray());
        UBArray array = value.asArray();
        assertEquals(2, array.size());
        assertTrue(array.get(0).isNumber());
        assertEquals(255, array.get(0).asInt());
        assertTrue(array.get(1).isNumber());
        assertEquals(1, array.get(1).asInt());

    }

    @Test
    public void readOptimizedArrayString() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] {'[', '$', 'S', '#', 'U', 0x2, 'U', 0x1, 'a', 'U', 0x1, 'b' };
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isArray());
        UBArray array = value.asArray();
        assertEquals(2, array.size());
        assertTrue(array.get(0).isString());
        assertEquals("a", array.get(0).asString());

        assertTrue(array.get(1).isString());
        assertEquals("b", array.get(1).asString());

        assertTrue(array.isStronglyTyped());
        assertEquals(UBArray.ArrayType.String, array.getStrongType());
    }

    @Test
    public void readObject() throws Exception {
        byte[] data;
        UBReader reader;
        UBValue value;

        data = new byte[] { '{', 'i', 3, 'l', 'a', 't', 'i', 5, 'i', 3, 'l', 'n', 'g', 'U', (byte)0xff, '}'};
        reader = new UBReader(new ByteArrayInputStream(data));
        value = reader.read();
        assertTrue(value.isObject());
        UBObject obj = value.asObject();
        assertTrue(obj.containsKey("lat"));
        assertEquals(5, obj.get("lat").asInt());
        assertEquals(255, obj.get("lng").asInt());
    }

    @Test
    public void readData() throws Exception {
        byte[] data;
        UBReader reader;

        byte[] expected = new byte[10000];
        Random r = new Random(1);
        r.nextBytes(expected);

        data = new byte[10003];
        data[0] = 'I';
        data[1] = 39;
        data[2] = 16;
        System.arraycopy(expected, 0, data, 3, 10000);

        reader = new UBReader(new ByteArrayInputStream(data));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        reader.readData(out);
        byte[] actual = out.toByteArray();

        assertEquals(10000, actual.length);
        assertArrayEquals(expected, actual);
    }
}
