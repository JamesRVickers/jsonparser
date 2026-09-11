package com.github.jamesrvickers.jsonparser;
import java.util.*;

/**
 * A {@link JsonNode} representing a JSON object: an ordered, mutable
 * mapping of string keys to {@link JsonNode} values.
 * <p>
 * Insertion order is preserved (backed by a {@link LinkedHashMap}), and
 * {@code JsonObject} is iterable over its entries in that order.
 */
public class JsonObject extends JsonNode implements Iterable<Map.Entry<String, JsonNode>> {
    private final LinkedHashMap<String, JsonNode> map;

    /**
     * Constructs a new, empty {@code JsonObject}.
     */
    public JsonObject() {
        this.map = new LinkedHashMap<>();
    }

    /**
     * Constructs a {@code JsonObject} backed directly by the given map.
     *
     * @param map the map to back this object; not copied
     */
    public JsonObject(LinkedHashMap<String, JsonNode> map) {
        this.map = map;
    }

    /**
     * Associates the given key with a string value, wrapping it in a
     * {@link JsonString}.
     *
     * @param key   the key
     * @param value the string value to associate with {@code key}
     * @return this object, for chaining
     */
    public JsonObject put(String key, String value) { return put(key, Json.node(value)); }

    /**
     * Associates the given key with an int value, wrapping it in a
     * {@link JsonNumber}.
     *
     * @param key   the key
     * @param value the int value to associate with {@code key}
     * @return this object, for chaining
     */
    public JsonObject put(String key, int value) { return put(key, Json.node(value)); }

    /**
     * Associates the given key with a double value, wrapping it in a
     * {@link JsonNumber}.
     *
     * @param key   the key
     * @param value the double value to associate with {@code key}
     * @return this object, for chaining
     */
    public JsonObject put(String key, double value) { return put(key, Json.node(value)); }

    /**
     * Associates the given key with a float value, wrapping it in a
     * {@link JsonNumber}.
     *
     * @param key   the key
     * @param value the float value to associate with {@code key}
     * @return this object, for chaining
     */
    public JsonObject put(String key, float value) { return put(key, Json.node(value)); }

    /**
     * Associates the given key with a long value, wrapping it in a
     * {@link JsonNumber}.
     *
     * @param key   the key
     * @param value the long value to associate with {@code key}
     * @return this object, for chaining
     */
    public JsonObject put(String key, long value) { return put(key, Json.node(value)); }

    /**
     * Associates the given key with a {@link JsonNode} value. If the key is
     * already present, its previous value is replaced.
     *
     * @param key  the key
     * @param node the node to associate with {@code key}
     * @return this object, for chaining
     */
    public JsonObject put(String key, JsonNode node) {
        map.put(key, node);
        return this;
    }

    /**
     * Copies all entries from another {@code JsonObject} into this one,
     * overwriting any existing keys in common.
     *
     * @param other the object whose entries should be copied in
     * @return this object, for chaining
     */
    public JsonObject putAll(JsonObject other) { map.putAll(other.map); return this; }

    /**
     * Copies all entries from the given map into this object, overwriting
     * any existing keys in common.
     *
     * @param other the entries to copy in
     * @return this object, for chaining
     */
    public JsonObject putAll(Map<String, JsonNode> other) { map.putAll(other); return this; }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key to look up
     * @return the {@link JsonNode} associated with {@code key}, or {@code null} if absent
     */
    public JsonNode get(String key) { return map.get(key); }

    /**
     * Removes the entry for the given key, if present.
     *
     * @param key the key to remove
     * @return this object, for chaining
     */
    public JsonObject remove(String key) { map.remove(key); return this; }

    /**
     * Returns whether this object has an entry for the given key.
     *
     * @param key the key to check for
     * @return {@code true} if this object contains {@code key}
     */
    public boolean contains(String key) { return map.containsKey(key); }

    /**
     * Copies all entries from another object into this one. Equivalent to
     * {@link #putAll(JsonObject)}.
     *
     * @param other the object whose entries should be merged in
     * @return this object, for chaining
     */
    public JsonObject merge(JsonObject other) { return this.putAll(other); }

    /**
     * Returns the number of key-value pairs in this object.
     *
     * @return the size of this object
     */
    public int size() { return map.size(); }

    /**
     * Returns whether this object has no entries.
     *
     * @return {@code true} if this object is empty
     */
    public boolean isEmpty() { return map.isEmpty(); }

    /**
     * Returns the backing map for this object, in insertion order.
     * <p>
     * Note: this returns a direct reference to the backing map, not a copy
     * or unmodifiable view; changes to the returned map affect this object.
     *
     * @return the underlying {@link LinkedHashMap} of this object
     */
    public LinkedHashMap<String, JsonNode> getMap() { return map; }

    /**
     * Returns this object.
     *
     * @return this {@code JsonObject}
     */
    @Override
    public JsonObject asObject() {
        return this;
    }

    /**
     * Returns an iterator over the entries of this object, in insertion order.
     *
     * @return an iterator over this object's key-value pairs
     */
    @Override
    public Iterator<Map.Entry<String, JsonNode>> iterator() {
        return map.entrySet().iterator();
    }

    /**
     * Returns the JSON text representation of this object, e.g.
     * {@code {"key":"value"}}.
     *
     * @return the JSON text for this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, JsonNode> entry : map.entrySet()) {
            if (!first) sb.append(",");
            first = false;
            sb.append(new JsonString(entry.getKey()).toString());
            sb.append(":");
            sb.append(entry.getValue().toString());
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Compares this object to another for equality. Two {@code JsonObject}s
     * are equal if they are of the same class and contain equal entries.
     *
     * @param object the object to compare against
     * @return {@code true} if {@code object} is an equal {@code JsonObject}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonObject other = (JsonObject)object;
        return map.equals(other.map);
    }

    /**
     * Returns a hash code for this object, based on its entries.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() { return map.hashCode(); }
}