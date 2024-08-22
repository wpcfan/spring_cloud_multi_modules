package com.twigcodes.cms.models.vm;

import com.twigcodes.cms.models.BlockConfig;
import com.twigcodes.cms.models.enumeration.BlockType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "创建页面区块")
public record CreatePageBlock(
    @Schema(description = "区块标题")
    String title,
    @Schema(description = "区块类型")
    BlockType type,
    @Schema(description = "区块配置")
    BlockConfig config
) {
}
