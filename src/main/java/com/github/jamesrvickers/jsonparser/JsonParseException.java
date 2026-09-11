package com.github.jamesrvickers.jsonparser;

/**
 * Thrown to indicate that a JSON document could not be parsed, or that a
 * {@link JsonNode} was accessed as a type it does not represent.
 */
public class JsonParseException extends RuntimeException {

    /**
     * Constructs a new {@code JsonParseException} with the given detail message.
     *
     * @param message the detail message describing the parse failure
     */
    public JsonParseException(String message) {
        super(message);
    }
}