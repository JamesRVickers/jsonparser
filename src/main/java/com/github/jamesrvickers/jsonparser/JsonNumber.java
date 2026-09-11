package com.github.jamesrvickers.jsonparser;

public class JsonNumber extends JsonNode {
    private final Number value;

    public JsonNumber(double value) {
        this.value = value;
    }

    @Override 
    public int asInt() { return value.intValue(); }

    @Override 
    public double asDouble() { return value.doubleValue(); }
    
    @Override 
    public float asFloat() { return value.floatValue(); }

    @Override 
    public long asLong() { return value.longValue(); }

    @Override
    public String toString() {
        if (!Double.isInfinite(value.doubleValue()) 
            && !Double.isNaN(value.doubleValue()) 
            && value.doubleValue() == Math.floor(value.doubleValue())) {
            return String.valueOf(value.longValue());
        }
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        JsonNumber other = (JsonNumber)object;
        return Double.compare(value.doubleValue(), other.value.doubleValue()) == 0;
    }

    @Override
    public int hashCode() { return Double.hashCode(value.doubleValue()); }
}
