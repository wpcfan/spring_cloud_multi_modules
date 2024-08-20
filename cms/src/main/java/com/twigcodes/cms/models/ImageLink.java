package com.twigcodes.cms.models;

import com.twigcodes.cms.models.enumeration.ImageLinkType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;

@Builder
@Schema(description = "图片链接")
public record ImageLink(
    @Schema(description = "图片链接类型", example = "url")
    ImageLinkType type,
    @Schema(description = "图片链接值", example = "https://www.baidu.com")
    String value
) implements Serializable {
}
