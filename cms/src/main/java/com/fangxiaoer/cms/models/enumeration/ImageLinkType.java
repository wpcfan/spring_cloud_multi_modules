package com.fangxiaoer.cms.models.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "图片链接类型", examples = {
    "https://www.baidu.com",
    "/home",
    "app://home"
})
public enum ImageLinkType {
    URL,
    ROUTE,
    SCHEME
}
