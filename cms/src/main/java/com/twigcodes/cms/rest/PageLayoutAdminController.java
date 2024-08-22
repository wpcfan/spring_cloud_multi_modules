package com.twigcodes.cms.rest;

import com.twigcodes.cms.models.PageBlock;
import com.twigcodes.cms.models.PageBlockData;
import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.BlockType;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.CreateOrUpdatePageLayout;
import com.twigcodes.cms.models.vm.PublishPageLayout;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import com.twigcodes.cms.services.PageLayoutAdminService;
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
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate startTimeFrom,
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate startTimeTo,
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate endTimeFrom,
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate endTimeTo,
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

    @Operation(summary = "创建页面布局")
    @PostMapping("")
    public PageLayout create(@RequestBody CreateOrUpdatePageLayout createPageLayout) {
        return pageLayoutService.create(createPageLayout);
    }

    @Operation(summary = "更新页面布局")
    @PutMapping("/{id}")
    public PageLayout update(@PathVariable String id, @RequestBody CreateOrUpdatePageLayout updatePageLayout) {
        return pageLayoutService.update(id, updatePageLayout);
    }

    @Operation(summary = "删除页面布局")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        pageLayoutService.delete(id);
    }

    @Operation(summary = "发布页面布局")
    @PostMapping("/{id}/publish")
    public PageLayout publish(@PathVariable String id, @RequestBody PublishPageLayout publishPageLayout) {
        return pageLayoutService.publish(id, publishPageLayout.startTime(), publishPageLayout.endTime());
    }

    @Operation(summary = "取消发布页面布局")
    @PostMapping("/{id}/unpublish")
    public PageLayout unpublish(@PathVariable String id) {
        return pageLayoutService.draft(id);
    }

    @Operation(summary = "归档页面布局")
    @PostMapping("/{id}/archive")
    public PageLayout archive(@PathVariable String id) {
        return pageLayoutService.archive(id);
    }

    @Operation(summary = "添加区块")
    @PostMapping("/{id}/blocks")
    public PageLayout addBlock(@PathVariable String id, @RequestBody PageBlock block) {
        return pageLayoutService.addBlock(id, block);
    }

    @Operation(summary = "删除区块")
    @DeleteMapping("/{id}/blocks/{blockId}")
    public PageLayout removeBlock(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.removeBlock(id, blockId);
    }

    @Operation(summary = "更新区块")
    @PutMapping("/{id}/blocks/{blockId}")
    public PageLayout updateBlock(@PathVariable String id, @PathVariable String blockId, @RequestBody PageBlock block) {
        return pageLayoutService.updateBlock(id, blockId, block);
    }

    @Operation(summary = "向上移动区块一位")
    @PostMapping("/{id}/blocks/{blockId}/move-up")
    public PageLayout moveBlockUp(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockUp(id, blockId);
    }

    @Operation(summary = "向下移动区块一位")
    @PostMapping("/{id}/blocks/{blockId}/move-down")
    public PageLayout moveBlockDown(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockDown(id, blockId);
    }

    @Operation(summary = "移动区块到顶部")
    @PostMapping("/{id}/blocks/{blockId}/move-top")
    public PageLayout moveBlockTop(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockTop(id, blockId);
    }

    @Operation(summary = "移动区块到底部")
    @PostMapping("/{id}/blocks/{blockId}/move-bottom")
    public PageLayout moveBlockBottom(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.moveBlockBottom(id, blockId);
    }

    @Operation(summary = "移动区块到指定位置")
    @PostMapping("/{id}/blocks/{blockId}/move-to")
    public PageLayout moveBlockTo(@PathVariable String id, @PathVariable String blockId, @RequestParam Integer sort) {
        return pageLayoutService.moveBlockTo(id, blockId, sort);
    }

    @Operation(summary = "复制区块")
    @PostMapping("/{id}/blocks/{blockId}/copy")
    public PageLayout copyBlock(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.copyBlock(id, blockId);
    }

    @Operation(summary = "添加区块数据")
    @PostMapping("/{id}/blocks/{blockId}/data")
    public PageLayout addBlockData(@PathVariable String id, @PathVariable String blockId, @RequestBody PageBlockData data) {
        return pageLayoutService.addBlockData(id, blockId, data);
    }

    @Operation(summary = "更新区块数据")
    @PutMapping("/{id}/blocks/{blockId}/data/{dataId}")
    public PageLayout updateBlockData(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId, @RequestBody PageBlockData data) {
        return pageLayoutService.updateBlockData(id, blockId, dataId, data);
    }

    @Operation(summary = "删除区块数据")
    @DeleteMapping("/{id}/blocks/{blockId}/data/{dataId}")
    public PageLayout removeBlockData(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.removeBlockData(id, blockId, dataId);
    }
}
