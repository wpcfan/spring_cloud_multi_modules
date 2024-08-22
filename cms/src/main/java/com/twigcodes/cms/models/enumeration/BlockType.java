package com.twigcodes.cms.models.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum BlockType {
    Banner("banner"),
    ImageRow("image_row"),
    ProductRow("product_row"),
    Waterfall("waterfall");

    private final String value;

    BlockType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static BlockType fromValue(String value) {
        for (BlockType blockType : BlockType.values()) {
            if (blockType.value.equals(value)) {
                return blockType;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
