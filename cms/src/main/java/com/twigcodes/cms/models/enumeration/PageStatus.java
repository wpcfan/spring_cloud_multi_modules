package com.twigcodes.cms.models.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PageStatus {
    DRAFT("draft"),
    PUBLISHED("published"),
    ARCHIVED("archived");

    private final String value;

    PageStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PageStatus fromValue(String value) {
        for (PageStatus pageStatus : PageStatus.values()) {
            if (pageStatus.value.equals(value)) {
                return pageStatus;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

