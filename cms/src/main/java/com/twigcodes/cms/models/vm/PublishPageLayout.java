package com.twigcodes.cms.models.vm;

import com.twigcodes.cms.validators.time.TimeComparison;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@TimeComparison(
    firstField = "startTime",
    secondField = "endTime",
    mode = TimeComparison.ComparisonMode.BEFORE,
    message = "生效时间必须早于失效时间"
)
@Schema(
    description = "发布页面布局",
    example = """
{
  "startTime": "2021-07-01 00:00:00",
  "endTime": "2021-07-01 23:59:59"
}
""")
public record PublishPageLayout(
    @Schema(description = "生效时间", type = "string", format = "date-time", example = "2021-07-01 00:00:00")
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime,
    @Schema(description = "失效时间", type = "string", format = "date-time", example = "2021-07-01 23:59:59")
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime
) {
}
