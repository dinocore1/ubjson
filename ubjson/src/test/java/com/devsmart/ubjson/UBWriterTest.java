package com.devsmart.ubjson;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

public class UBWriterTest {

    @Test
    public void testWriteNull() throws IOException {
        UBValue value;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UBWriter writer = new UBWriter(out);
        value = UBValueFactory.createNull();
        writer.write(value);

        byte[] array = out.toByteArray();
        assertEquals(1, array.length);
        assertEquals('Z', array[0]);
    }

    @Test
    public void testWriteBool() throws IOException {

        UBValue value;
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createBool(true);
        writer.write(value);

        array = out.toByteArray();
        assertEquals(1, array.length);
        assertEquals('T', array[0]);

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createBool(false);
        writer.write(value);

        array = out.toByteArray();
        assertEquals(1, array.length);
        assertEquals('F', array[0]);

    }

    @Test
    public void testWriteChar() throws IOException {

        UBValue value;
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createChar('t');
        writer.write(value);

        array = out.toByteArray();
        assertEquals(2, array.length);
        assertEquals('C', array[0]);
        assertEquals('t', array[1]);

    }

    @Test
    public void testWriteInt() throws IOException {

        UBValue value;
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createInt(255);
        writer.write(value);
        array = out.toByteArray();
        assertEquals(2, array.length);
        assertEquals('U', array[0]);
        assertEquals(255, (short)(0xFF & array[1]));

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createInt(-1);
        writer.write(value);
        array = out.toByteArray();
        assertEquals(2, array.length);
        assertEquals('i', array[0]);
        assertEquals(255, (short)(0xFF & array[1]));

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createInt(29753);
        writer.write(value);
        array = out.toByteArray();
        assertEquals(3, array.length);
        assertEquals('I', array[0]);
        assertEquals(0x74, (short)(0xFF & array[1]));
        assertEquals(0x39, (short)(0xFF & array[2]));

    }

    @Test
    public void testWriteLongInt() throws IOException {
        UBValue value;
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        long longValue = Long.valueOf("1456707337000");

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createInt(longValue);
        writer.write(value);
        array = out.toByteArray();
        assertEquals(9, array.length);
        assertEquals('L', array[0]);
        assertEquals(0x28, (short)(0xFF & array[8]));
        assertEquals(0xFB, (short)(0xFF & array[7]));
        assertEquals(0x85, (short)(0xFF & array[6]));
        assertEquals(0x2A, (short)(0xFF & array[5]));
        assertEquals(0x53, (short)(0xFF & array[4]));
        assertEquals(0x01, (short)(0xFF & array[3]));
        assertEquals(0x00, (short)(0xFF & array[2]));
        assertEquals(0x00, (short)(0xFF & array[1]));
    }

    @Test
    public void testWriteArray() throws IOException {
        UBValue value;
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        value = UBValueFactory.createArray(new byte[]{ 0x3, 0x4});
        writer.write(value);
        array = out.toByteArray();

        assertEquals('[', array[0]);
        if(array[1] == '$') {
            assertTrue(array[2] == 'i' || array[2] == 'U');
            assertEquals('#', array[3]);
            assertTrue(array[4] == 'i' || array[4] == 'U');
            assertEquals(2, array[5]);

            assertEquals(0x3, array[6]);
            assertEquals(0x4, array[7]);
            assertEquals(8, array.length);

        } else if(array[1] == '#') {
            assertTrue(array[2] == 'i' || array[2] == 'U');
            assertEquals(2, array[3]);
        } else {
            assertTrue(array[2] == 'i' || array[2] == 'U');
            assertEquals(3, array[3]);
            assertTrue(array[4] == 'i' || array[4] == 'U');
            assertEquals(4, array[5]);
            assertEquals(']', array[6]);

        }

    }

    @Test
    public void testWriteObject() throws Exception {
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        UBObject obj = UBValueFactory.createObject();
        obj.put("hello", UBValueFactory.createString("world"));
        obj.put("array", UBValueFactory.createArray(new int[] {1,2,3}));

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        writer.write(obj);
        array = out.toByteArray();

        assertEquals('{', array[0]);

    }

    @Test
    public void testWriteStringArray() throws Exception {
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        writer.write(UBValueFactory.createArray(new String[]{"a", "b", "c"}));
        array = out.toByteArray();

        assertEquals('[', array[0]);
        assertEquals('$', array[1]);
        assertEquals('S', array[2]);
        assertEquals('#', array[3]);
        assertEquals('U', array[4]);
        assertEquals(0x3, array[5]);

        assertEquals('U', array[6]);
        assertEquals(0x1, array[7]);
        assertEquals('a', array[8]);

        assertEquals('U', array[9]);
        assertEquals(0x1, array[10]);
        assertEquals('b', array[11]);

        assertEquals('U', array[12]);
        assertEquals(0x1, array[13]);
        assertEquals('c', array[14]);

        assertEquals(15, array.length);
    }

    @Test
    public void testWriteByteArray() throws Exception {

        byte[] data = new byte[30];
        Random r = new Random(1);
        r.nextBytes(data);

        UBValue value = UBValueFactory.createArray( UBValueFactory.createArray(data) );

        ByteArrayOutputStream out;
        UBWriter writer;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);
        writer.write(value);

        ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
        UBReader reader = new UBReader(input);

        UBValue outputValue = reader.read();
        assertTrue(outputValue.isArray());
        assertEquals(1, outputValue.asArray().size());
        assertEquals(30, outputValue.asArray().get(0).asByteArray().length);

    }

    @Test
    public void testWriteData() throws Exception {
        ByteArrayOutputStream out;
        UBWriter writer;
        byte[] array;

        out = new ByteArrayOutputStream();
        writer = new UBWriter(out);

        byte[] data = new byte[10000];
        Random r = new Random(1);
        r.nextBytes(data);

        writer.writeData(data.length, new ByteArrayInputStream(data));
        array = out.toByteArray();

        assertEquals('I', array[0]);
        assertEquals(39, array[1]);
        assertEquals(16, array[2]);

        byte[] actual = new byte[10000];
        System.arraycopy(array, 3, actual, 0, 10000);
        assertArrayEquals(data, actual);

    }


}
