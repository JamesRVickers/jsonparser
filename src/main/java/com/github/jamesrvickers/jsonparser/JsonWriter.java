package com.github.jamesrvickers.jsonparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public final class JsonWriter {
    private Path path;
    private JsonObject root;

    public JsonWriter() {
        this.path = null;
        this.root = null;
    }

    public JsonWriter(Path path, JsonObject root) {
        setPath(path);
        this.root = root;
    }

    public JsonWriter(File file, JsonObject root) {
        setPath(file);
        this.root = root;
    }

    public boolean write() {
        return writeTo(
            this.path,
            this.root
        );
    }

    public static boolean writeTo(Path path, JsonObject root) {
        String content = root.toString();
        try {
            Files.writeString(path, content);
            return true;
        } catch (IOException e) { throw new JsonWriteException(e.getMessage()); }
    }
    
    public void setPath(Path path) { this.path = path; }
    public void setPath(File file) { this.path = Path.of(file.toURI()); }

    public void setRoot(JsonObject root) { this.root = root; }

    public Path getPath() { return path; }
    public JsonObject getRoot() { return root; }
}
