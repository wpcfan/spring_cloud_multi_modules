package com.twigcodes.cms.models.vm;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PublishPageLayout(
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime
) {
}
