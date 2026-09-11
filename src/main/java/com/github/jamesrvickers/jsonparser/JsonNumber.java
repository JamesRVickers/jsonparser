package com.github.jamesrvickers.jsonparser;

/**
 * A {@link JsonNode} representing a JSON numeric value.
 * <p>
 * Internally, values are stored as a {@code double}; the {@code asXxx()}
 * accessors narrow or widen this stored value on demand.
 */
public class JsonNumber extends JsonNode {
    private final Number value;

    /**
     * Constructs a {@code JsonNumber} wrapping the given value.
     *
     * @param value the numeric value to wrap
     */
    public JsonNumber(double value) {
        this.value = value;
    }

    /**
     * Returns this node's value narrowed to an {@code int}.
     *
     * @return the int value of this node
     */
    @Override
    public int asInt() { return value.intValue(); }

    /**
     * Returns this node's value as a {@code double}.
     *
     * @return the double value of this node
     */
    @Override
    public double asDouble() { return value.doubleValue(); }

    /**
     * Returns this node's value narrowed to a {@code float}.
     *
     * @return the float value of this node
     */
    @Override
    public float asFloat() { return value.floatValue(); }

    /**
     * Returns this node's value narrowed to a {@code long}.
     *
     * @return the long value of this node
     */
    @Override
    public long asLong() { return value.longValue(); }

    /**
     * Returns the JSON text representation of this number.
     * <p>
     * If the value is finite and has no fractional part, it is rendered as
     * an integer (e.g. {@code 3} rather than {@code 3.0}); otherwise it is
     * rendered using its default {@code double} string form.
     *
     * @return the JSON text for this number
     */
    @Override
    public String toString() {
        if (!Double.isInfinite(value.doubleValue())
            && !Double.isNaN(value.doubleValue())
            && value.doubleValue() == Math.floor(value.doubleValue())) {
            return String.valueOf(value.longValue());
        }
        return String.valueOf(value);
    }

    /**
     * Compares this number to another object for equality. Two
     * {@code JsonNumber}s are equal if they are of the same class and their
     * {@code double} values compare equal via {@link Double#compare}.
     *
     * @param object the object to compare against
     * @return {@code true} if {@code object} is an equal {@code JsonNumber}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonNumber other = (JsonNumber)object;
        return Double.compare(value.doubleValue(), other.value.doubleValue()) == 0;
    }

    /**
     * Returns a hash code for this number, based on its {@code double} value.
     *
     * @return a hash code value for this number
     */
    @Override
    public int hashCode() { return Double.hashCode(value.doubleValue()); }
}