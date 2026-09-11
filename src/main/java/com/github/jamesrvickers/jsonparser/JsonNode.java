package com.github.jamesrvickers.jsonparser;

import java.io.Serializable;

public abstract class JsonNode implements Serializable {
    public JsonObject asObject() { throw new JsonParseException("Not a JsonObject"); }
    public JsonArray asArray() { throw new JsonParseException("Not a JsonArray"); }
    public String asString() { throw new JsonParseException("Not a String"); }
    public int asInt() { throw new JsonParseException("Not an int"); }
    public double asDouble() { throw new JsonParseException("Not a double"); }
    public float asFloat() { throw new JsonParseException("Not a float"); }
    public long asLong() { throw new JsonParseException("Not a long"); }
    public Boolean asBoolean() { throw new JsonParseException("Not a Boolean"); }

    @Override
    public String toString() { return ""; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof JsonNode)) return false;
        return super.equals(object);
    }

    @Override
    public int hashCode() { return super.hashCode(); }
}
