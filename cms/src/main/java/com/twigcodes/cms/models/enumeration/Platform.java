package com.twigcodes.cms.models.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "平台")
public enum Platform {
    APP,
    WEB,
    H5,
    MINI_APP,
    FLUTTER_APP
}

