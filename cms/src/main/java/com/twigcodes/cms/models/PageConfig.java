package com.twigcodes.cms.models;

import com.twigcodes.cms.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.io.Serializable;

@Jacksonized
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "页面配置")
public class PageConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "页面基准宽度", example = "375")
    @Min(value = 300, message = "不能小于 300")
    @Max(value = 1920, message = "不能大于 1920")
    private Double baselineScreenWidth;

    @Schema(description = "页面的水平内边距", example = "12.0")
    @Min(value = 1, message = "必须大于等于 1")
    @Max(value = 48, message = "必须小于等于 48")
    private Double horizontalPadding;

    @Schema(description = "页面的垂直内边距", example = "12.0")
    @Min(value = 1, message = "必须大于等于 1")
    @Max(value = 48, message = "必须小于等于 48")
    private Double verticalPadding;

    @Schema(description = "页面的背景颜色", example = "#FFFFFF")
    @Pattern(regexp = Constants.HEX_COLOR_REGEX_WITH_HASH, message = "必须是合法的颜色值")
    private String backgroundColor;

    @Schema(description = "页面的背景图片", example = "https://picsum.photos/375/600")
    @Pattern(regexp = Constants.URL_REGEX, message = "必须是合法的 URL")
    private String backgroundImage;
}
