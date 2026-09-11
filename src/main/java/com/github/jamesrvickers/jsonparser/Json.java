package com.github.jamesrvickers.jsonparser;

import java.io.*;

public class Json {
    public static final JsonString TRUE = new JsonString("true");
    public static final JsonString FALSE = new JsonString("false");
    public static final JsonString NULL = new JsonString("null");

    public Json() {}

    public static JsonObject parse(String input) {
        JsonParser parser = new JsonParser(input);
        return parser.parse();
    }

    public static boolean write(JsonObject root, File path) {
        JsonWriter writer = new JsonWriter(path, root);
        return writer.write();
    }

    public static JsonNode node (String value) { return new JsonString(value); }
    public static JsonNode node (int value) { return new JsonNumber(value); } 
    public static JsonNode node (double value) { return new JsonNumber(value); }
    public static JsonNode node (float value) { return new JsonNumber(value); }
    public static JsonNode node (long value) { return new JsonNumber(value); }
    public static JsonObject object () { return new JsonObject(); }
    public static JsonArray array(JsonNode... nodes) {
        JsonArray arr = new JsonArray();
        for (JsonNode n : nodes) arr.add(n);
        return arr;
    }
}