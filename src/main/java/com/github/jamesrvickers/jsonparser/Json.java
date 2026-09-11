package com.github.jamesrvickers.jsonparser;

import java.io.*;

/**
 * Entry point for the JSON parser library, providing static convenience
 * methods for parsing, writing, and constructing {@link JsonNode} instances.
 * <p>
 * This class is not intended to be instantiated; all functionality is
 * exposed through static methods.
 */
public class Json {

    /** A {@link JsonString} constant representing the literal {@code true}. */
    public static final JsonString TRUE = new JsonString("true");

    /** A {@link JsonString} constant representing the literal {@code false}. */
    public static final JsonString FALSE = new JsonString("false");

    /** A {@link JsonString} constant representing the literal {@code null}. */
    public static final JsonString NULL = new JsonString("null");

    /**
     * Constructs a new {@code Json} instance.
     * <p>
     * This class exposes only static functionality and does not need to be
     * instantiated.
     */
    public Json() {}

    /**
     * Parses a JSON-formatted string into a {@link JsonObject}.
     *
     * @param input the JSON text to parse; the outermost element must be an object
     * @return the parsed {@link JsonObject} representing the root of the document
     * @throws JsonParseException if {@code input} is not valid JSON
     */
    public static JsonObject parse(String input) {
        JsonParser parser = new JsonParser(input);
        return parser.parse();
    }

    /**
     * Writes a {@link JsonObject} to a file as JSON text.
     *
     * @param root the JSON object to write
     * @param path the file to write to
     * @return {@code true} if the write succeeded
     * @throws JsonWriteException if an I/O error occurs while writing
     */
    public static boolean write(JsonObject root, File path) {
        JsonWriter writer = new JsonWriter(path, root);
        return writer.write();
    }

    /**
     * Creates a {@link JsonNode} wrapping a string value.
     *
     * @param value the string value to wrap
     * @return a new {@link JsonString} containing {@code value}
     */
    public static JsonNode node(String value) { return new JsonString(value); }

    /**
     * Creates a {@link JsonNode} wrapping an int value.
     *
     * @param value the int value to wrap
     * @return a new {@link JsonNumber} containing {@code value}
     */
    public static JsonNode node(int value) { return new JsonNumber(value); }

    /**
     * Creates a {@link JsonNode} wrapping a double value.
     *
     * @param value the double value to wrap
     * @return a new {@link JsonNumber} containing {@code value}
     */
    public static JsonNode node(double value) { return new JsonNumber(value); }

    /**
     * Creates a {@link JsonNode} wrapping a float value.
     *
     * @param value the float value to wrap
     * @return a new {@link JsonNumber} containing {@code value}
     */
    public static JsonNode node(float value) { return new JsonNumber(value); }

    /**
     * Creates a {@link JsonNode} wrapping a long value.
     *
     * @param value the long value to wrap
     * @return a new {@link JsonNumber} containing {@code value}
     */
    public static JsonNode node(long value) { return new JsonNumber(value); }

    /**
     * Creates a new, empty {@link JsonObject}.
     *
     * @return a new empty {@link JsonObject}
     */
    public static JsonObject object() { return new JsonObject(); }

    /**
     * Creates a new {@link JsonArray} populated with the given nodes, in order.
     *
     * @param nodes the nodes to add to the array
     * @return a new {@link JsonArray} containing {@code nodes}
     */
    public static JsonArray array(JsonNode... nodes) {
        JsonArray arr = new JsonArray();
        for (JsonNode n : nodes) arr.add(n);
        return arr;
    }
}