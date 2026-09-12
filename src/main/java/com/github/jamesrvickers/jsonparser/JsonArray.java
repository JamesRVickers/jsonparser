package com.github.jamesrvickers.jsonparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A {@link JsonNode} representing a JSON array: an ordered, mutable
 * sequence of {@link JsonNode} values.
 * <p>
 * {@code JsonArray} is iterable over its elements in insertion order.
 */
public class JsonArray extends JsonNode implements Iterable<JsonNode> {
    private final List<JsonNode> nodes;

    /**
     * Constructs a new, empty {@code JsonArray}.
     */
    public JsonArray() {
        this.nodes = new ArrayList<>();
    }

    /**
     * Constructs a {@code JsonArray} containing a copy of the given nodes,
     * in order.
     *
     * @param nodes the initial elements of the array; must not be {@code null}
     */
    public JsonArray(List<JsonNode> nodes) {
        assert nodes != null : "Array nodes is null";
        this.nodes = new ArrayList<>(nodes);
    }

    // doing all of these java docs scare me

    /**
     * Appends a string value to this array, wrapping it in a {@link JsonString}.
     *
     * @param value the string value to add
     * @return this array, for chaining
     */
    public JsonArray add(String value) { return add(Json.node(value)); }

    /**
     * Appends an int value to this array, wrapping it in a {@link JsonNumber}.
     *
     * @param value the int value to add
     * @return this array, for chaining
     */
    public JsonArray add(int value) { return add(Json.node(value)); }

    /**
     * Appends a double value to this array, wrapping it in a {@link JsonNumber}.
     *
     * @param value the double value to add
     * @return this array, for chaining
     */
    public JsonArray add(double value) { return add(Json.node(value)); }

    /**
     * Appends a float value to this array, wrapping it in a {@link JsonNumber}.
     *
     * @param value the float value to add
     * @return this array, for chaining
     */
    public JsonArray add(float value) { return add(Json.node(value)); }

    /**
     * Appends a long value to this array, wrapping it in a {@link JsonNumber}.
     *
     * @param value the long value to add
     * @return this array, for chaining
     */
    public JsonArray add(long value) { return add(Json.node(value)); }

    /**
     * Appends a {@link JsonNode} to the end of this array.
     *
     * @param node the node to add
     * @return this array, for chaining
     */
    public JsonArray add(JsonNode node) {
        nodes.add(node);
        return this;
    }

    /**
     * Appends all elements of another {@code JsonArray} to this array, in order.
     *
     * @param other the array whose elements should be appended
     * @return this array, for chaining
     */
    public JsonArray addAll(JsonArray other) { nodes.addAll(other.nodes); return this; }

    /**
     * Appends all elements of the given list to this array, in order.
     *
     * @param other the nodes to append
     * @return this array, for chaining
     */
    public JsonArray addAll(List<JsonNode> other) { nodes.addAll(other); return this; }

    /**
     * Returns the element at the given index.
     *
     * @param index the index of the element to return
     * @return the {@link JsonNode} at {@code index}
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public JsonNode get(int index) { return nodes.get(index); }

    /**
     * Removes the first occurrence of the given node from this array, if present.
     *
     * @param node the node to remove
     * @return this array, for chaining
     */
    public JsonArray remove(JsonNode node) { nodes.remove(node); return this; }

    /**
     * Returns whether this array contains the given node.
     *
     * @param node the node to check for
     * @return {@code true} if this array contains {@code node}
     */
    public boolean contains(JsonNode node) { return nodes.contains(node); }

    /**
     * Appends all elements of another array to this array. Equivalent to
     * {@link #addAll(JsonArray)}.
     *
     * @param other the array whose elements should be merged in
     * @return this array, for chaining
     */
    public JsonArray merge(JsonArray other) { return this.addAll(other); }

    /**
     * Returns the number of elements in this array.
     *
     * @return the size of this array
     */
    public int size() { return nodes.size(); }

    /**
     * Returns whether this array has no elements.
     *
     * @return {@code true} if this array is empty
     */
    public boolean isEmpty() { return nodes.isEmpty(); }

    /**
     * Returns an unmodifiable view of the elements in this array, in order.
     *
     * @return an unmodifiable {@link List} of this array's elements
     */
    public List<JsonNode> getNodes() { return Collections.unmodifiableList(nodes); }

    /**
     * Returns this array.
     *
     * @return this {@code JsonArray}
     */
    @Override
    public JsonArray asArray() {
        return this;
    }

    /**
     * Returns an iterator over the elements of this array, in order.
     *
     * @return an iterator over this array's elements
     */
    @Override
    public Iterator<JsonNode> iterator() {
        return nodes.iterator();
    }

    /**
     * Returns the JSON text representation of this array, e.g. {@code [1,2,3]}.
     *
     * @return the JSON text for this array
     */
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

    /**
     * Compares this array to another object for equality. Two arrays are
     * equal if they are of the same class and contain equal elements in the
     * same order.
     *
     * @param object the object to compare against
     * @return {@code true} if {@code object} is an equal {@code JsonArray}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonArray other = (JsonArray)object;
        return nodes.equals(other.nodes);
    }

    /**
     * Returns a hash code for this array, based on its elements.
     *
     * @return a hash code value for this array
     */
    @Override
    public int hashCode() { return nodes.hashCode(); }
}