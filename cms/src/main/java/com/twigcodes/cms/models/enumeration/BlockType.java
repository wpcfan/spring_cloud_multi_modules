package com.twigcodes.cms.models.enumeration;

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
}