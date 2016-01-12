# UBJSON for Java #

[Universal Binary JSON](http://ubjson.org/) is a binary JSON specification
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
UBObject obj = UBValueFactory.createObject();
obj.put("hello", UBValueFactory.createString("world"));
obj.put("array", UBValueFactory.createArray(new int[] {1,2,3}));

ByteArrayOutputStream out = new ByteArrayOutputStream();
UBWriter writer = new UBWriter(out);
writer.write(value);
writer.close();

```


Read from stream:

```

UBReader reader = new UBReader(in);

UBValue value = reader.read();
assert(value.isObject());
UBObject obj = value.asObject();
String value = obj.get("hello").asString();

reader.close();

```