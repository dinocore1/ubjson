# UBJSON for Java #

(Universal Binary JSON)[http://ubjson.org/] is a binary JSON specification
designed for ultimate read performance; bringing simplicity, size and
performance all together into a single specification that is 100% compatible
with JSON.

Include in gradle project:

```
dependencies {
  compile 'com.dev-smart:ubjson:0.1.0'
  ...
}
```

Write a UBValue to a stream:

```
UBValue value = UBValueFactory.createString("hello world");

ByteArrayOutputStream out = new ByteArrayOutputStream();
UBWriter writer = new UBWriter(out);
writer.write(value);
writer.close();

```


Read from stream:

```

UBReader reader = new UBReader(in);

UBValue value = reader.read();
reader.close();

```