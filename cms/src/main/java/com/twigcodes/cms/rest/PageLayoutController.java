package com.twigcodes.cms.rest;

import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.BlockType;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import com.twigcodes.cms.services.PageLayoutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Tags(
    {
        @Tag(name = "Page Layout", description = "Page Layout API")
    }
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/page-layouts")
public class PageLayoutController {
    private final PageLayoutService pageLayoutService;

    @GetMapping("")
    public PageLayout get(
        @RequestHeader(name = "target") TargetPage targetPage,
        @RequestHeader(name = "platform") Platform platform
    ) {
        val now = LocalDateTime.now();
        return pageLayoutService.get(targetPage, platform, now);
    }
}
