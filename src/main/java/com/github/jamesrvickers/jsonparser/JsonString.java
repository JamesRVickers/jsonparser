package com.github.jamesrvickers.jsonparser;

public class JsonString extends JsonNode {
    private final String value;

    public JsonString(String value) {
        assert value != null : "String value is null";
        this.value = value;
    }

    @Override
    public String asString() { return value; }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonString other = (JsonString)object;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }
}