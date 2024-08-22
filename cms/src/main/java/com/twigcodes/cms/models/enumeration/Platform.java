package com.twigcodes.cms.models.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Platform {
    APP("app"),
    WEB("web");

    private final String value;

    Platform(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Platform fromValue(String value) {
        for (Platform platform : Platform.values()) {
            if (platform.value.equals(value)) {
                return platform;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

