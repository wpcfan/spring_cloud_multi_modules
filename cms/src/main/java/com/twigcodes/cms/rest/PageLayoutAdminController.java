package com.twigcodes.cms.rest;

import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.BlockType;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import com.twigcodes.cms.services.PageLayoutAdminService;
import com.twigcodes.cms.services.PageLayoutService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/page-layouts")
public class PageLayoutAdminController {

    private final PageLayoutAdminService pageLayoutService;

    @GetMapping("")
    public Page<PageLayout> search(
        @RequestParam String title,
        @RequestParam PageStatus status,
        @RequestParam TargetPage targetPage,
        @RequestParam Platform platform,
        @RequestParam BlockType blockType,
        @DateTimeFormat(pattern = "yyyyMMdd") @RequestParam(required = false) LocalDate startTimeFrom,
        @DateTimeFormat(pattern = "yyyyMMdd") @RequestParam(required = false) LocalDate startTimeTo,
        @DateTimeFormat(pattern = "yyyyMMdd") @RequestParam(required = false) LocalDate endTimeFrom,
        @DateTimeFormat(pattern = "yyyyMMdd") @RequestParam(required = false) LocalDate endTimeTo,
        @ParameterObject Pageable pageable
    ) {
        val queryPageLayout = QueryPageLayout.builder()
            .title(title)
            .startTimeFrom(startTimeFrom != null ? startTimeFrom.atStartOfDay() : null)
            .startTimeTo(startTimeTo != null ? startTimeTo.atTime(23, 59, 59) : null)
            .endTimeFrom(endTimeFrom != null ? endTimeFrom.atStartOfDay() : null)
            .endTimeTo(endTimeTo != null ? endTimeTo.atTime(23, 59, 59) : null)
            .status(status)
            .targetPage(targetPage)
            .platform(platform)
            .blockType(blockType)
            .build();
        return pageLayoutService.searchPageLayouts(queryPageLayout, pageable);
    }
}
