package com.twigcodes.cms.models.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "页面状态")
public enum PageStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED
}

