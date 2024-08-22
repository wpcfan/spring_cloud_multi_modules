package com.twigcodes.cms.models.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TargetPage {
    Home("home"),
    Category("category"),
    About("about"),;

    private final String value;

    TargetPage(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TargetPage fromValue(String value) {
        for (TargetPage targetPage : TargetPage.values()) {
            if (targetPage.value.equals(value)) {
                return targetPage;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

