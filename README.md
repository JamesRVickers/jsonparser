# jsonparser

A small, dependency-free JSON parsing and writing library for Java.

`jsonparser` reads JSON text into a tree of simple node objects
(`JsonObject`, `JsonArray`, `JsonString`, `JsonNumber`), lets you build
that tree up in code with a fluent API, and writes it back out as JSON
text.

## Features

- Parse JSON from a `String`, `File`, or `Path`
- Build JSON documents in code with chainable `put`/`add` methods
- Write a `JsonObject` back out to a file
- Loop over objects and arrays directly (`JsonObject`/`JsonArray`
  implement `Iterable`)
- No external dependencies. Just `java.util.Scanner` and `java.nio.file`

## Installation

This library isn't published to a package repository yet. To use it,
clone the repo and build it locally, or copy the
`com.github.jamesrvickers.jsonparser` package into your project.

```bash
git clone https://github.com/jamesrvickers/jsonparser.git
cd jsonparser
# build with your tool of choice, e.g.
javac -d out src/main/java/com/github/jamesrvickers/jsonparser/*.java
```

## Quick start

### Parsing JSON

```java
import com.github.jamesrvickers.jsonparser.*;

String input = """
    {
      "name": "Ada Lovelace",
      "born": 1815,
      "tags": ["mathematician", "writer"]
    }
    """;

JsonObject person = Json.parse(input);

String name = person.get("name").asString();      // "Ada Lovelace"
int born = person.get("born").asInt();             // 1815
JsonArray tags = person.get("tags").asArray();

for (JsonNode tag : tags) {
    System.out.println(tag.asString());
}
```

You can also parse straight from a file:

```java
JsonParser parser = new JsonParser(new File("data.json"));
JsonObject root = parser.parse();
```

### Building JSON

```java
import com.github.jamesrvickers.jsonparser.*;

JsonObject person = Json.object()
    .put("name", "Ada Lovelace")
    .put("born", 1815)
    .put("tags", Json.array(
        Json.node("mathematician"),
        Json.node("writer")
    ));

System.out.println(person);
// {"name":"Ada Lovelace","born":1815,"tags":["mathematician","writer"]}
```

### Writing JSON to a file

```java
JsonObject data = Json.object().put("status", "ok");

Json.write(data, new File("output.json"));

// or, with more control:
JsonWriter writer = new JsonWriter(new File("output.json"), data);
writer.write();
```

## API overview

| Class                | What it does                                                        |
|-----------------------|----------------------------------------------------------------------|
| `Json`                | Static entry point: `parse`, `write`, and node/object/array factories |
| `JsonNode`             | Base type for every value in a JSON tree                            |
| `JsonObject`           | An ordered map of string keys to `JsonNode` values                  |
| `JsonArray`            | An ordered list of `JsonNode` values                                 |
| `JsonString`           | A string value (also used for the `true`/`false`/`null` literals)   |
| `JsonNumber`           | A numeric value, stored internally as a `double`                     |
| `JsonParser`           | Turns JSON text into a `JsonObject`                                  |
| `JsonWriter`           | Writes a `JsonObject` out to a file as JSON text                     |
| `JsonParseException`   | Thrown on invalid JSON or a bad node type conversion                 |
| `JsonWriteException`   | Thrown when writing a JSON document to a file fails                  |

### Reading values off a node

Every `JsonNode` has typed accessors like `asObject()`, `asArray()`,
`asString()`, `asInt()`, `asDouble()`, `asFloat()`, `asLong()`, and
`asBoolean()`. Each one throws `JsonParseException` if the node isn't
actually that type.

## Notes

- The top-level element of any parsed document has to be a JSON object.
  `Json.parse` and `JsonParser.parse()` both return a `JsonObject`.
- `JsonObject` keeps insertion order (it's backed by a `LinkedHashMap`).

## License

## License
[![License: Unlicense](https://img.shields.io/badge/license-Unlicense-blue.svg)](https://unlicense.org/)

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <https://unlicense.org>
