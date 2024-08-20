package com.twigcodes.cms.models;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.io.Serializable;

@Jacksonized
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TextContent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private String backgroundColor;
    private String textColor;
    @Builder.Default
    private Integer fontSize = 12;
    @Builder.Default
    private String fontFamily = "Arial";
    @Builder.Default
    private String textAlign = "left";
    @Builder.Default
    private boolean bold = false;
    @Builder.Default
    private boolean italic = false;
}
