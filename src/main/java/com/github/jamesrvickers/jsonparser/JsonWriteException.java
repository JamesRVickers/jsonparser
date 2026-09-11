package com.github.jamesrvickers.jsonparser;

/**
 * Thrown to indicate that a JSON document could not be written to disk,
 * typically wrapping an underlying {@link java.io.IOException}.
 */
public class JsonWriteException extends RuntimeException {

    /**
     * Constructs a new {@code JsonWriteException} with the given detail message.
     *
     * @param message the detail message describing the write failure
     */
    public JsonWriteException(String message) {
        super(message);
    }
}