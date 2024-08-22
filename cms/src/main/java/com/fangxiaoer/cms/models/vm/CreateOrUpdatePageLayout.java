package com.fangxiaoer.cms.models.vm;

import com.fangxiaoer.cms.models.PageConfig;
import com.fangxiaoer.cms.models.enumeration.Platform;
import com.fangxiaoer.cms.models.enumeration.TargetPage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "创建或更新页面布局")
public record CreateOrUpdatePageLayout (
    @Schema(description = "标题", example = "首页")
    @Size(min = 2, max = 255, message = "标题长度必须在 2 到 255 之间")
    String title,
    @Schema(description = "目标页面", example = "HOME")
    TargetPage targetPage,
    @Schema(description = "平台", example = "APP")
    Platform platform,
    @Schema(description = "页面配置")
    PageConfig config
){
}
