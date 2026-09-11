package com.github.jamesrvickers.jsonparser;

import java.io.Serializable;

/**
 * Base type for every node in a parsed JSON document tree.
 * <p>
 * A {@code JsonNode} represents any JSON value: an object, an array, a
 * string, a number, a boolean, or {@code null}. Concrete subclasses
 * ({@link JsonObject}, {@link JsonArray}, {@link JsonString},
 * {@link JsonNumber}) override the relevant {@code asXxx()} accessor for
 * their type; all other accessors throw {@link JsonParseException} by
 * default, since a node cannot be converted to a type it does not represent.
 */
public abstract class JsonNode implements Serializable {

    /**
     * Returns this node as a {@link JsonObject}.
     *
     * @return this node, cast to {@link JsonObject}
     * @throws JsonParseException if this node is not a {@link JsonObject}
     */
    public JsonObject asObject() { throw new JsonParseException("Not a JsonObject"); }

    /**
     * Returns this node as a {@link JsonArray}.
     *
     * @return this node, cast to {@link JsonArray}
     * @throws JsonParseException if this node is not a {@link JsonArray}
     */
    public JsonArray asArray() { throw new JsonParseException("Not a JsonArray"); }

    /**
     * Returns this node's underlying string value.
     *
     * @return the string value of this node
     * @throws JsonParseException if this node is not a {@link JsonString}
     */
    public String asString() { throw new JsonParseException("Not a String"); }

    /**
     * Returns this node's underlying numeric value as an {@code int}.
     *
     * @return the int value of this node
     * @throws JsonParseException if this node is not a {@link JsonNumber}
     */
    public int asInt() { throw new JsonParseException("Not an int"); }

    /**
     * Returns this node's underlying numeric value as a {@code double}.
     *
     * @return the double value of this node
     * @throws JsonParseException if this node is not a {@link JsonNumber}
     */
    public double asDouble() { throw new JsonParseException("Not a double"); }

    /**
     * Returns this node's underlying numeric value as a {@code float}.
     *
     * @return the float value of this node
     * @throws JsonParseException if this node is not a {@link JsonNumber}
     */
    public float asFloat() { throw new JsonParseException("Not a float"); }

    /**
     * Returns this node's underlying numeric value as a {@code long}.
     *
     * @return the long value of this node
     * @throws JsonParseException if this node is not a {@link JsonNumber}
     */
    public long asLong() { throw new JsonParseException("Not a long"); }

    /**
     * Returns this node's underlying boolean value.
     *
     * @return the boolean value of this node
     * @throws JsonParseException if this node does not represent a boolean
     */
    public Boolean asBoolean() { throw new JsonParseException("Not a Boolean"); }

    /**
     * Returns the JSON text representation of this node.
     * <p>
     * The base implementation returns an empty string; subclasses override
     * this to produce their actual JSON representation.
     *
     * @return the JSON text for this node
     */
    @Override
    public String toString() { return ""; }

    /**
     * Compares this node to another object for equality.
     *
     * @param object the object to compare against
     * @return {@code true} if {@code object} is a {@link JsonNode} equal to this one
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof JsonNode)) return false;
        return super.equals(object);
    }

    /**
     * Returns a hash code for this node.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() { return super.hashCode(); }
}