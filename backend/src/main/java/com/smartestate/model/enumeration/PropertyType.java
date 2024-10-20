package com.smartestate.model.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PropertyType {
    APARTMENT,
    HOUSE,
    LAND,
    COMMERCIAL;

    @JsonValue
    public String getValue() {
        return this.name();
    }

    @JsonCreator
    public static PropertyType fromValue(String value) {
        if (value != null) {
            for (PropertyType type : PropertyType.values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
