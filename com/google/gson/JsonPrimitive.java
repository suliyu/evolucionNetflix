// 
// Decompiled by Procyon v0.5.30
// 

package com.google.gson;

import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigInteger;

public final class JsonPrimitive extends JsonElement
{
    private static final Class<?>[] PRIMITIVE_TYPES;
    private Object value;
    
    static {
        PRIMITIVE_TYPES = new Class[] { Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class };
    }
    
    public JsonPrimitive(final Boolean value) {
        this.setValue(value);
    }
    
    public JsonPrimitive(final Number value) {
        this.setValue(value);
    }
    
    JsonPrimitive(final Object value) {
        this.setValue(value);
    }
    
    public JsonPrimitive(final String value) {
        this.setValue(value);
    }
    
    private static boolean isIntegral(final JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive.value instanceof Number) {
            final Number n = (Number)jsonPrimitive.value;
            return n instanceof BigInteger || n instanceof Long || n instanceof Integer || n instanceof Short || n instanceof Byte;
        }
        return false;
    }
    
    private static boolean isPrimitiveOrString(final Object o) {
        if (!(o instanceof String)) {
            final Class<?> class1 = o.getClass();
            final Class<?>[] primitive_TYPES = JsonPrimitive.PRIMITIVE_TYPES;
            for (int length = primitive_TYPES.length, i = 0; i < length; ++i) {
                if (primitive_TYPES[i].isAssignableFrom(class1)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = false;
        if (this != o) {
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final JsonPrimitive jsonPrimitive = (JsonPrimitive)o;
            if (this.value == null) {
                if (jsonPrimitive.value != null) {
                    return false;
                }
            }
            else if (isIntegral(this) && isIntegral(jsonPrimitive)) {
                if (this.getAsNumber().longValue() != jsonPrimitive.getAsNumber().longValue()) {
                    return false;
                }
            }
            else {
                if (this.value instanceof Number && jsonPrimitive.value instanceof Number) {
                    final double doubleValue = this.getAsNumber().doubleValue();
                    final double doubleValue2 = jsonPrimitive.getAsNumber().doubleValue();
                    if (doubleValue != doubleValue2) {
                        boolean b2 = b;
                        if (!Double.isNaN(doubleValue)) {
                            return b2;
                        }
                        b2 = b;
                        if (!Double.isNaN(doubleValue2)) {
                            return b2;
                        }
                    }
                    return true;
                }
                return this.value.equals(jsonPrimitive.value);
            }
        }
        return true;
    }
    
    @Override
    public boolean getAsBoolean() {
        if (this.isBoolean()) {
            return this.getAsBooleanWrapper();
        }
        return Boolean.parseBoolean(this.getAsString());
    }
    
    @Override
    Boolean getAsBooleanWrapper() {
        return (Boolean)this.value;
    }
    
    @Override
    public double getAsDouble() {
        if (this.isNumber()) {
            return this.getAsNumber().doubleValue();
        }
        return Double.parseDouble(this.getAsString());
    }
    
    @Override
    public float getAsFloat() {
        if (this.isNumber()) {
            return this.getAsNumber().floatValue();
        }
        return Float.parseFloat(this.getAsString());
    }
    
    @Override
    public int getAsInt() {
        if (this.isNumber()) {
            return this.getAsNumber().intValue();
        }
        return Integer.parseInt(this.getAsString());
    }
    
    @Override
    public long getAsLong() {
        if (this.isNumber()) {
            return this.getAsNumber().longValue();
        }
        return Long.parseLong(this.getAsString());
    }
    
    @Override
    public Number getAsNumber() {
        if (this.value instanceof String) {
            return new LazilyParsedNumber((String)this.value);
        }
        return (Number)this.value;
    }
    
    @Override
    public String getAsString() {
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        }
        if (this.isBoolean()) {
            return this.getAsBooleanWrapper().toString();
        }
        return (String)this.value;
    }
    
    @Override
    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (isIntegral(this)) {
            final long longValue = this.getAsNumber().longValue();
            return (int)(longValue ^ longValue >>> 32);
        }
        if (this.value instanceof Number) {
            final long doubleToLongBits = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
        }
        return this.value.hashCode();
    }
    
    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }
    
    public boolean isNumber() {
        return this.value instanceof Number;
    }
    
    public boolean isString() {
        return this.value instanceof String;
    }
    
    void setValue(final Object value) {
        if (value instanceof Character) {
            this.value = String.valueOf((char)value);
            return;
        }
        $Gson$Preconditions.checkArgument(value instanceof Number || isPrimitiveOrString(value));
        this.value = value;
    }
}
