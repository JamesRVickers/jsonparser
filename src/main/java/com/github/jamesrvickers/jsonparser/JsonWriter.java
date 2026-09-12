package com.github.jamesrvickers.jsonparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Writes a {@link JsonObject} to a file as JSON text.
 * <p>
 * A {@code JsonWriter} can be configured once with a target path and root
 * object and reused via {@link #write()}, or used statically via
 * {@link #writeTo(Path, JsonObject)} for a one-off write.
 */
public final class JsonWriter {
    private Path path;
    private JsonObject root;

    /**
     * Constructs a {@code JsonWriter} with no path or root object set.
     * <p>
     * {@link #setPath(Path)} (or {@link #setPath(File)}) and
     * {@link #setRoot(JsonObject)} must be called before {@link #write()}.
     */
    public JsonWriter() {
        this.path = null;
        this.root = null;
    }

    /**
     * Constructs a {@code JsonWriter} for the given path and root object.
     *
     * @param path the file to write to
     * @param root the JSON object to write
     */
    public JsonWriter(Path path, JsonObject root) {
        setPath(path);
        this.root = root;
    }

    /**
     * Constructs a {@code JsonWriter} for the given file and root object.
     *
     * @param file the file to write to
     * @param root the JSON object to write
     */
    public JsonWriter(File file, JsonObject root) {
        setPath(file);
        this.root = root;
    }

    /**
     * Writes this writer's configured root object to its configured path.
     *
     * @return {@code true} if the write succeeded
     * @throws JsonWriteException if an I/O error occurs while writing
     */
    public boolean write() {
        return writeTo(
            this.path,
            this.root
        );
    }

    /**
     * Writes the given {@link JsonObject} to the given path as JSON text,
     * overwriting any existing file content.
     *
     * @param path the file to write to
     * @param root the JSON object to write
     * @return {@code true} if the write succeeded
     * @throws JsonWriteException if an I/O error occurs while writing
     */
    public static boolean writeTo(Path path, JsonObject root) {
        String content = root.toString();
        try {
            Files.writeString(path, content);
            return true;
        } catch (IOException e) { throw new JsonWriteException(e.getMessage()); }
    }

    /**
     * Sets the target path this writer writes to.
     *
     * @param path the file to write to
     */
    public void setPath(Path path) { this.path = path; }

    /**
     * Sets the target path this writer writes to.
     *
     * @param file the file to write to
     */
    public void setPath(File file) { this.path = Path.of(file.toURI()); }

    /**
     * Sets the root object this writer writes.
     *
     * @param root the JSON object to write
     */
    public void setRoot(JsonObject root) { this.root = root; }

    /**
     * Returns the target path this writer writes to.
     *
     * @return the configured output path
     */
    public Path getPath() { return path; }

    /**
     * Returns the root object this writer writes.
     *
     * @return the configured root {@link JsonObject}
     */
    public JsonObject getRoot() { return root; }
}