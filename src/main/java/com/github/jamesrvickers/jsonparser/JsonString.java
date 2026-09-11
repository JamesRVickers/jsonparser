package com.github.jamesrvickers.jsonparser;

/**
 * A {@link JsonNode} representing a JSON string value.
 * <p>
 * This class is also used internally to represent the JSON literals
 * {@code true}, {@code false}, and {@code null} (see {@link Json#TRUE},
 * {@link Json#FALSE}, {@link Json#NULL}).
 */
public class JsonString extends JsonNode {
    private final String value;

    /**
     * Constructs a {@code JsonString} wrapping the given value.
     *
     * @param value the string value to wrap; must not be {@code null}
     */
    public JsonString(String value) {
        assert value != null : "String value is null";
        this.value = value;
    }

    /**
     * Returns this node's underlying string value, unescaped.
     *
     * @return the string value of this node
     */
    @Override
    public String asString() { return value; }

    /**
     * Returns the JSON text representation of this string, quoted and with
     * {@code "}, {@code \}, newline, carriage return, and tab characters
     * escaped.
     *
     * @return the JSON text for this string, including surrounding quotes
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> sb.append(c);
            }
        }
        sb.append("\"");
        return sb.toString();
    }

    /**
     * Compares this string node to another object for equality. Two
     * {@code JsonString}s are equal if they are of the same class and wrap
     * equal string values.
     *
     * @param object the object to compare against
     * @return {@code true} if {@code object} is an equal {@code JsonString}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonString other = (JsonString)object;
        return value.equals(other.value);
    }

    /**
     * Returns a hash code for this string node, based on its value.
     *
     * @return a hash code value for this node
     */
    @Override
    public int hashCode() { return value.hashCode(); }
}