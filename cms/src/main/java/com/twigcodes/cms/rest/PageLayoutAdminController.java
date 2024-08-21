package com.twigcodes.cms.rest;

import com.twigcodes.cms.models.BlockData;
import com.twigcodes.cms.models.PageBlock;
import com.twigcodes.cms.models.PageBlockData;
import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.BlockType;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.PublishPageLayout;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import com.twigcodes.cms.services.PageLayoutAdminService;
import com.twigcodes.cms.services.PageLayoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/page-layouts")
public class PageLayoutAdminController {

    private final PageLayoutAdminService pageLayoutService;

    @Operation(summary = "查询页面布局")
    @GetMapping("")
    public Page<PageLayout> search(
        @Parameter @RequestParam String title,
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

    @PostMapping("")
    public PageLayout create(@RequestBody PageLayout pageLayout) {
        return pageLayoutService.create(pageLayout);
    }

    @PutMapping("/{id}")
    public PageLayout update(@PathVariable String id, @RequestBody PageLayout pageLayout) {
        return pageLayoutService.update(id, pageLayout);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        pageLayoutService.delete(id);
    }

    @PostMapping("/{id}/publish")
    public PageLayout publish(@PathVariable String id, @RequestBody PublishPageLayout publishPageLayout) {
        return pageLayoutService.publish(id, publishPageLayout.startTime(), publishPageLayout.endTime());
    }

    @PostMapping("/{id}/unpublish")
    public PageLayout unpublish(@PathVariable String id) {
        return pageLayoutService.draft(id);
    }

    @PostMapping("/{id}/archive")
    public PageLayout archive(@PathVariable String id) {
        return pageLayoutService.archive(id);
    }

    @PostMapping("/{id}/blocks")
    public PageLayout addBlock(@PathVariable String id, @RequestBody PageBlock block) {
        return pageLayoutService.addBlock(id, block);
    }

    @DeleteMapping("/{id}/blocks/{blockId}")
    public PageLayout removeBlock(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.removeBlock(id, blockId);
    }

    @PutMapping("/{id}/blocks/{blockId}")
    public PageLayout updateBlock(@PathVariable String id, @PathVariable String blockId, @RequestBody PageBlock block) {
        return pageLayoutService.updateBlock(id, blockId, block);
    }

    @PostMapping("/{id}/blocks/{blockId}/move-up")
    public PageLayout moveBlockUp(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockUp(id, blockId);
    }

    @PostMapping("/{id}/blocks/{blockId}/move-down")
    public PageLayout moveBlockDown(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockDown(id, blockId);
    }

    @PostMapping("/{id}/blocks/{blockId}/move-top")
    public PageLayout moveBlockTop(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockTop(id, blockId);
    }

    @PostMapping("/{id}/blocks/{blockId}/move-bottom")
    public PageLayout moveBlockBottom(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockBottom(id, blockId);
    }

    @PostMapping("/{id}/blocks/{blockId}/move-to")
    public PageLayout moveBlockTo(@PathVariable String id, @PathVariable String blockId, @RequestParam Integer sort) {
        return pageLayoutService.moveBlockTo(id, blockId, sort);
    }

    @PostMapping("/{id}/blocks/{blockId}/copy")
    public PageLayout copyBlock(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.copyBlock(id, blockId);
    }

    @PostMapping("/{id}/blocks/{blockId}/data")
    public PageLayout addBlockData(@PathVariable String id, @PathVariable String blockId, @RequestBody PageBlockData data) {
        return pageLayoutService.addBlockData(id, blockId, data);
    }

    @PutMapping("/{id}/blocks/{blockId}/data/{dataId}")
    public PageLayout updateBlockData(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId, @RequestBody PageBlockData data) {
        return pageLayoutService.updateBlockData(id, blockId, dataId, data);
    }

    @DeleteMapping("/{id}/blocks/{blockId}/data/{dataId}")
    public PageLayout removeBlockData(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.removeBlockData(id, blockId, dataId);
    }
}
