package com.github.jamesrvickers.jsonparser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public final class JsonParser {
    public static final Predicate<Scanner> OPEN_BRACE = (s) -> s.hasNext("\\{");
    public static final Predicate<Scanner> CLOSE_BRACE = (s) -> s.hasNext("\\}");
    public static final Predicate<Scanner> OPEN_BRACKET = (s) -> s.hasNext("\\[");
    public static final Predicate<Scanner> CLOSE_BRACKET = (s) -> s.hasNext("\\]");
    public static final Predicate<Scanner> QUOTE = (s) -> s.hasNext("\"");
    public static final Predicate<Scanner> COLON = (s) -> s.hasNext(":");
    public static final Predicate<Scanner> COMMA = (s) -> s.hasNext(",");

    private String json;
    private JsonObject root;

    public JsonParser() {
        this.json = null;
        this.root = null;
    }

    public JsonParser(String json) {
        assert json != null : "Json can not be null";
        this.json = json;
        this.root = null;
    }

    public JsonParser(Path path) {
        assert path != null : "Path can not be null";
        this.setJson(path);
        this.root = null;
    }

    public JsonParser(File file) {
        assert file != null : "File can not be null";
        this.setJson(file);
        this.root = null;
    }

    public void setJson(Path file) {
        try { this.json = Files.readString(file); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public void setJson(File file) {
        Path path = Path.of(file.toURI());
        try { this.json = Files.readString(path); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public JsonObject parse() {
        root = this.parse(new Scanner(json));
        return root;
    }

    public JsonObject parse(Scanner s) {
        String regex = "\\s+|(?!\\s)(?=[\\\"\\{\\}\\[\\]\\:,])|(?<=[\\\"\\{\\}\\[\\]\\:,])(?<!\\s)";
        s.useDelimiter(regex);
        root = parseObject(s);
        return root;
    }

    static String require(Predicate<Scanner> predicate, Scanner s, String message) {
        assert predicate.test(s);
        return s.next();
    }

    private JsonObject parseObject(Scanner s) {
        require(OPEN_BRACE, s, "No open brace for object");
        JsonObject obj = Json.object();

        if (!CLOSE_BRACE.test(s)) {
            String key = parseKey(s);
            require(COLON, s, "No colon after key");
            JsonNode node = parseValue(s);
            obj.put(key, node);

            while (COMMA.test(s)) {
                s.next();
                key = parseKey(s);
                require(COLON, s, "No colon after key");
                node = parseValue(s);
                obj.put(key, node);
            }
        }

        require(CLOSE_BRACE, s, "No close brace for object");
        return obj;
    }

    private JsonNode parseValue(Scanner s) {
        if (OPEN_BRACE.test(s)) return parseObject(s);
        else if (OPEN_BRACKET.test(s)) return parseArray(s);
        else if (QUOTE.test(s)) {
            require(QUOTE, s, "No quote around string value [start]");
            String value = readQuotedContent(s);
            require(QUOTE, s, "No quote around string value [end]");
            return new JsonString(value);
        } else {
            String token = s.next();
            return switch (token) {
                case "true" -> Json.TRUE;
                case "false" -> Json.FALSE;
                case "null" -> Json.NULL;
                default -> Json.node(Double.parseDouble(token));
            };
        }
    }

    private JsonArray parseArray(Scanner s) {
        require(OPEN_BRACKET, s, "No open bracket for array");
        JsonArray arr = Json.array();

        if (!CLOSE_BRACKET.test(s)) {
            arr.add(parseValue(s));
            while (COMMA.test(s)) {
                s.next();
                arr.add(parseValue(s));
            }
        }

        require(CLOSE_BRACKET, s, "No close bracket for array");
        return arr;
    }

    private String parseKey(Scanner s) {
        require(QUOTE, s, "No quote around var key [start]");
        String key = readQuotedContent(s);
        require(QUOTE, s, "No quote around var key [end]");
        return key;
    }

    private String readQuotedContent(Scanner s) {
        List<String> words = new ArrayList<>();
        while (!QUOTE.test(s)) words.add(s.next());
        return String.join(" ", words);
    }

    public String getJson() { return json; }
    public JsonObject getRoot() { return root; }
}