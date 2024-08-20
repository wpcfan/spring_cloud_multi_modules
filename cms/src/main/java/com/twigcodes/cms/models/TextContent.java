package com.twigcodes.cms.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@Setter
public class TextContent {
    private String title;
    private String backgroundColor;
    private String textColor;
    private String fontSize;
    private String fontFamily;
    private String textAlign;
    private boolean bold;
    private boolean italic;
}
