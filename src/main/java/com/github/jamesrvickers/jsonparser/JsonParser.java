package com.github.jamesrvickers.jsonparser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

/**
 * A hand-written, recursive-descent JSON parser.
 * <p>
 * A {@code JsonParser} is constructed from a JSON source (a string, file, or
 * path), and {@link #parse()} tokenizes and parses it into a tree of
 * {@link JsonNode} objects rooted at a {@link JsonObject}. The parser is
 * built around a {@link Scanner} configured with a delimiter regex that
 * splits input on whitespace and around JSON structural characters
 * ({@code { } [ ] : ,} and {@code "}), so that each call to
 * {@link Scanner#next()} returns either a structural token or a run of
 * content between them.
 */
public final class JsonParser {

    /** Matches when the scanner's next token is an open brace {@code {}. */
    public static final Predicate<Scanner> OPEN_BRACE = (s) -> s.hasNext("\\{");

    /** Matches when the scanner's next token is a close brace {@code }}. */
    public static final Predicate<Scanner> CLOSE_BRACE = (s) -> s.hasNext("\\}");

    /** Matches when the scanner's next token is an open bracket {@code [}. */
    public static final Predicate<Scanner> OPEN_BRACKET = (s) -> s.hasNext("\\[");

    /** Matches when the scanner's next token is a close bracket {@code ]}. */
    public static final Predicate<Scanner> CLOSE_BRACKET = (s) -> s.hasNext("\\]");

    /** Matches when the scanner's next token is a double quote {@code "}. */
    public static final Predicate<Scanner> QUOTE = (s) -> s.hasNext("\"");

    /** Matches when the scanner's next token is a colon {@code :}. */
    public static final Predicate<Scanner> COLON = (s) -> s.hasNext(":");

    /** Matches when the scanner's next token is a comma {@code ,}. */
    public static final Predicate<Scanner> COMMA = (s) -> s.hasNext(",");

    private String json;
    private JsonObject root;

    /**
     * Constructs a {@code JsonParser} with no source text set.
     * <p>
     * {@link #setJson(Path)} or {@link #setJson(File)} must be called before
     * {@link #parse()}.
     */
    public JsonParser() {
        this.json = null;
        this.root = null;
    }

    /**
     * Constructs a {@code JsonParser} for the given JSON source text.
     *
     * @param json the JSON text to parse; must not be {@code null}
     */
    public JsonParser(String json) {
        assert json != null : "Json can not be null";
        this.json = json;
        this.root = null;
    }

    /**
     * Constructs a {@code JsonParser} that reads its source text from the
     * given path.
     *
     * @param path the file to read JSON text from; must not be {@code null}
     */
    public JsonParser(Path path) {
        assert path != null : "Path can not be null";
        this.setJson(path);
        this.root = null;
    }

    /**
     * Constructs a {@code JsonParser} that reads its source text from the
     * given file.
     *
     * @param file the file to read JSON text from; must not be {@code null}
     */
    public JsonParser(File file) {
        assert file != null : "File can not be null";
        this.setJson(file);
        this.root = null;
    }

    /**
     * Reads the JSON source text for this parser from the given path.
     *
     * @param file the path to read from
     * @throws RuntimeException if an I/O error occurs while reading
     */
    public void setJson(Path file) {
        try { this.json = Files.readString(file); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    /**
     * Reads the JSON source text for this parser from the given file.
     *
     * @param file the file to read from
     * @throws RuntimeException if an I/O error occurs while reading
     */
    public void setJson(File file) {
        Path path = Path.of(file.toURI());
        try { this.json = Files.readString(path); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    /**
     * Parses this parser's configured JSON source text into a
     * {@link JsonObject}.
     *
     * @return the parsed root {@link JsonObject}
     * @throws JsonParseException if the source text is not valid JSON
     */
    public JsonObject parse() {
        root = this.parse(new Scanner(json));
        return root;
    }

    /**
     * Configures the given {@link Scanner} with this parser's delimiter
     * regex and parses a JSON object from it.
     *
     * @param s the scanner to read tokens from
     * @return the parsed root {@link JsonObject}
     * @throws JsonParseException if the scanned text is not a valid JSON object
     */
    public JsonObject parse(Scanner s) {
        String regex = "\\s+|(?!\\s)(?=[\\\"\\{\\}\\[\\]\\:,])|(?<=[\\\"\\{\\}\\[\\]\\:,])(?<!\\s)";
        s.useDelimiter(regex);
        root = parseObject(s);
        return root;
    }

    /**
     * Asserts that the scanner's next token satisfies the given predicate,
     * then consumes and returns that token.
     *
     * @param predicate the condition the next token must satisfy
     * @param s         the scanner to read from
     * @param message   a description used if the assertion fails
     * @return the consumed token
     */
    static String require(Predicate<Scanner> predicate, Scanner s, String message) {
        assert predicate.test(s);
        return s.next();
    }

    /**
     * Parses a JSON object, starting at an open brace and consuming through
     * its matching close brace.
     *
     * @param s the scanner positioned at the start of an object
     * @return the parsed {@link JsonObject}
     */
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

    /**
     * Parses a single JSON value: an object, array, quoted string, or a bare
     * token interpreted as {@code true}, {@code false}, {@code null}, or a
     * number.
     *
     * @param s the scanner positioned at the start of a value
     * @return the parsed {@link JsonNode}
     * @throws NumberFormatException if a bare token is not a recognized
     *                                literal and cannot be parsed as a double
     */
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

    /**
     * Parses a JSON array, starting at an open bracket and consuming through
     * its matching close bracket.
     *
     * @param s the scanner positioned at the start of an array
     * @return the parsed {@link JsonArray}
     */
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

    /**
     * Parses a quoted object key.
     *
     * @param s the scanner positioned at the opening quote of a key
     * @return the key text, unescaped
     */
    private String parseKey(Scanner s) {
        require(QUOTE, s, "No quote around var key [start]");
        String key = readQuotedContent(s);
        require(QUOTE, s, "No quote around var key [end]");
        return key;
    }

    /**
     * Reads and joins all tokens between the scanner's current position and
     * the next unconsumed quote character, reassembling them with single
     * spaces (since the delimiter regex splits on internal whitespace).
     *
     * @param s the scanner positioned just after an opening quote
     * @return the joined content up to, but not including, the closing quote
     */
    private String readQuotedContent(Scanner s) {
        List<String> words = new ArrayList<>();
        while (!QUOTE.test(s)) words.add(s.next());
        return String.join(" ", words);
    }

    /**
     * Returns this parser's configured JSON source text.
     *
     * @return the JSON source text, or {@code null} if not set
     */
    public String getJson() { return json; }

    /**
     * Returns the root object produced by the most recent call to
     * {@link #parse()} or {@link #parse(Scanner)}.
     *
     * @return the parsed root {@link JsonObject}, or {@code null} if not yet parsed
     */
    public JsonObject getRoot() { return root; }
}