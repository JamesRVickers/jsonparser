package com.github.jamesrvickers.jsonparser;
import java.util.*;

public class JsonObject extends JsonNode implements Iterable<Map.Entry<String, JsonNode>> {
    private final LinkedHashMap<String, JsonNode> map;

    public JsonObject() {
        this.map = new LinkedHashMap<>();
    }
    
    public JsonObject(LinkedHashMap<String, JsonNode> map) {
        this.map = map;
    }

    public JsonObject put(String key, String value) { return put(key, Json.node(value)); }
    public JsonObject put(String key, int value) { return put(key, Json.node(value)); }
    public JsonObject put(String key, double value) { return put(key, Json.node(value)); }
    public JsonObject put(String key, float value) { return put(key, Json.node(value)); }
    public JsonObject put(String key, long value) { return put(key, Json.node(value)); }
    public JsonObject put(String key, JsonNode node) {
        map.put(key, node);
        return this;
    }

    public JsonObject putAll(JsonObject other) { map.putAll(other.map); return this; }
    public JsonObject putAll(Map<String, JsonNode> other) { map.putAll(other); return this; }

    public JsonNode get(String key) { return map.get(key); }
    public JsonObject remove(String key) { map.remove(key); return this; }
    public boolean contains(String key) { return map.containsKey(key); }
    public JsonObject merge(JsonObject other) { return this.putAll(other); }
    public int size() { return map.size(); }
    public boolean isEmpty() { return map.isEmpty(); }
    public LinkedHashMap<String, JsonNode> getMap() { return map; }

    @Override
    public JsonObject asObject() {
        return this;
    }

    @Override
    public Iterator<Map.Entry<String, JsonNode>> iterator() {
        return map.entrySet().iterator();
    }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonObject other = (JsonObject)object;
        return map.equals(other.map);
    }

    @Override
    public int hashCode() { return map.hashCode(); }
}
