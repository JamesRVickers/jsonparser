package com.github.jamesrvickers.jsonparser;

import java.util.*;

public class JsonArray extends JsonNode implements Iterable<JsonNode> {
    private final List<JsonNode> nodes;
    
    public JsonArray() {
        this.nodes = new ArrayList<>();
    }

    public JsonArray(List<JsonNode> nodes) {
        assert nodes != null : "Array nodes is null";
        this.nodes = new ArrayList<>(nodes);
    }

    // doing all of these java docs scare me
    public JsonArray add(String value) { return add(Json.node(value)); }
    public JsonArray add(int value) { return add(Json.node(value)); }
    public JsonArray add(double value) { return add(Json.node(value)); }
    public JsonArray add(float value) { return add(Json.node(value)); }
    public JsonArray add(long value) { return add(Json.node(value)); }
    public JsonArray add(JsonNode node) {
        nodes.add(node);
        return this;
    }

    public JsonArray addAll(JsonArray other) { nodes.addAll(other.nodes); return this; }
    public JsonArray addAll(List<JsonNode> other) { nodes.addAll(other); return this; }

    public JsonNode get(int index) { return nodes.get(index); }
    public JsonArray remove(JsonNode node) { nodes.remove(node); return this; }
    public boolean contains(JsonNode node) { return nodes.contains(node); }
    public JsonArray merge(JsonArray other) { return this.addAll(other); }
    public int size() { return nodes.size(); }
    public boolean isEmpty() { return nodes.isEmpty(); }
    public List<JsonNode> getNodes() { return Collections.unmodifiableList(nodes); }

    @Override
    public JsonArray asArray() {
        return this;
    }
    
    @Override
    public Iterator<JsonNode> iterator() {
        return nodes.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nodes.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(nodes.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonArray other = (JsonArray)object;
        return nodes.equals(other.nodes);
    }

    @Override
    public int hashCode() { return nodes.hashCode(); }
}
