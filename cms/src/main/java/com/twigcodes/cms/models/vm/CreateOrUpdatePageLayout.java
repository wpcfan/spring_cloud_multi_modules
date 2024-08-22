package com.twigcodes.cms.models.vm;

import com.twigcodes.cms.models.PageConfig;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "创建或更新页面布局")
public record CreateOrUpdatePageLayout (
    @Schema(description = "标题", example = "首页")
    @Size(min = 2, max = 255, message = "标题长度必须在 2 到 255 之间")
    String title,
    @Schema(description = "目标页面", example = "home")
    TargetPage targetPage,
    @Schema(description = "平台", example = "app")
    Platform platform,
    @Schema(description = "页面配置")
    PageConfig config
){
}
