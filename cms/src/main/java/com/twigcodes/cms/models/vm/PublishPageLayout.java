package com.twigcodes.cms.models.vm;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PublishPageLayout(
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startTime,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate endTime
) {
}
