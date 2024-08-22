package com.fangxiaoer.cms.models.vm;

import com.fangxiaoer.cms.models.BlockConfig;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "更新页面区块")
public record UpdatePageBlock(
    @Schema(description = "区块标题")
    String title,
    @Schema(description = "区块配置")
    BlockConfig config
) {
}
