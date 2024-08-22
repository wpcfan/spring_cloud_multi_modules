package com.twigcodes.cms.rest;

import com.twigcodes.cms.models.BlockData;
import com.twigcodes.cms.models.PageBlock;
import com.twigcodes.cms.models.PageBlockData;
import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.BlockType;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.*;
import com.twigcodes.cms.services.PageLayoutAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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
        @Parameter(description = "页面标题") @RequestParam(required = false) String title,
        @Parameter(description = "页面状态") @RequestParam(required = false, name = "status") PageStatus status,
        @Parameter(description = "生效目标") @RequestParam(required = false) TargetPage targetPage,
        @Parameter(description = "平台") @RequestParam(required = false) Platform platform,
        @Parameter(description = "区块类型") @RequestParam(required = false) BlockType blockType,
        @Parameter(description = "生效时间查询时间段起点") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate startTimeFrom,
        @Parameter(description = "生效时间查询时间段终点") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate startTimeTo,
        @Parameter(description = "失效时间查询时间段起点") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate endTimeFrom,
        @Parameter(description = "失效时间查询时间段终点") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate endTimeTo,
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
    public PageLayout create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreateOrUpdatePageLayout.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "创建页面布局",
                        value = """
                        {
                            "title": "首页春季房交会活动",
                            "platform": "APP",
                            "targetPage": "HOME",
                            "config": {
                                "baselineScreenWidth": 375,
                                "horizontalPadding": 12,
                                "verticalPadding": 12,
                                "backgroundColor": "#FFFFFF",
                                "backgroundImage": "https://picsum.photos/375/600"
                            }
                        }
                        """
                    )
                }
            )
        )
        @Valid @RequestBody CreateOrUpdatePageLayout createPageLayout) {
        return pageLayoutService.create(createPageLayout);
    }

    @Operation(summary = "更新页面布局")
    @PutMapping("/{id}")
    public PageLayout update(
        @PathVariable String id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreateOrUpdatePageLayout.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "更新页面布局",
                        value = """
                        {
                            "title": "首页秋季房交会活动",
                            "platform": "APP",
                            "targetPage": "HOME",
                            "config": {
                                "baselineScreenWidth": 400,
                                "horizontalPadding": 16,
                                "verticalPadding": 16,
                                "backgroundColor": "#FFFFFF",
                                "backgroundImage": "https://picsum.photos/375/600"
                            }
                        }
                        """
                    )
                }
            )
        )
        @Valid @RequestBody CreateOrUpdatePageLayout updatePageLayout) {
        return pageLayoutService.update(id, updatePageLayout);
    }

    @Operation(summary = "删除页面布局")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        pageLayoutService.delete(id);
    }

    @Operation(summary = "发布页面布局")
    @PostMapping("/{id}/publish")
    public PageLayout publish(
        @PathVariable String id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PublishPageLayout.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "发布页面布局",
                        value = """
                        {
                            "startTime": "2032-09-01 00:00:00",
                            "endTime": "2032-12-30 23:59:59"
                        }
                        """
                    )
                }
            )
        )
        @Valid @RequestBody PublishPageLayout publishPageLayout) {
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
    public PageLayout addBlock(
        @PathVariable String id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreatePageBlock.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "添加图片区块",
                        value = """
                        {
                            "title": "图片行区块",
                            "type": "IMAGE_ROW",
                            "config": {
                               "horizontalPadding": 12,
                               "verticalPadding": 12,
                               "horizontalSpacing": 12,
                               "verticalSpacing": 12,
                               "blockHeight": 120,
                               "backgroundColor": "#FFFFFF",
                               "borderColor": "#FFFFFF",
                               "borderWidth": 1
                            }
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "添加房源区块",
                        value = """
                        {
                            "title": "房源区块",
                            "type": "HOUSE_ROW",
                            "config": {
                               "horizontalPadding": 12,
                               "verticalPadding": 12,
                               "horizontalSpacing": 12,
                               "verticalSpacing": 12,
                               "blockHeight": 120,
                               "backgroundColor": "#FFFFFF",
                               "borderColor": "#FFFFFF",
                               "borderWidth": 1
                            }
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "添加轮播图区块",
                        value = """
                        {
                            "title": "轮播图区块",
                            "type": "BANNER",
                            "config": {
                               "horizontalPadding": 12,
                               "verticalPadding": 12,
                               "horizontalSpacing": 12,
                               "verticalSpacing": 12,
                               "blockHeight": 120,
                               "backgroundColor": "#FFFFFF",
                               "borderColor": "#FFFFFF",
                               "borderWidth": 1
                            }
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "添加瀑布流区块",
                        value = """
                        {
                            "title": "瀑布流区块",
                            "type": "WATERFALL",
                            "config": {
                               "horizontalPadding": 12,
                               "verticalPadding": 12,
                               "horizontalSpacing": 12,
                               "verticalSpacing": 12,
                               "blockHeight": 120,
                               "backgroundColor": "#FFFFFF",
                               "borderColor": "#FFFFFF",
                               "borderWidth": 1
                            }
                        }
                        """
                    ),
                }
            )
        )
        @Valid @RequestBody CreatePageBlock createPageBlock) {
        val block = PageBlock.builder()
            .title(createPageBlock.title())
            .type(createPageBlock.type())
            .config(createPageBlock.config())
            .build();
        return pageLayoutService.addBlock(id, block);
    }

    @Operation(summary = "删除区块")
    @DeleteMapping("/{id}/blocks/{blockId}")
    public PageLayout removeBlock(@PathVariable String id, @PathVariable String blockId) {
        return pageLayoutService.removeBlock(id, blockId);
    }

    @Operation(summary = "更新区块")
    @PutMapping("/{id}/blocks/{blockId}")
    public PageLayout updateBlock(@PathVariable String id, @PathVariable String blockId, @Valid @RequestBody UpdatePageBlock updatePageBlock) {
        val block = PageBlock.builder()
            .title(updatePageBlock.title())
            .config(updatePageBlock.config())
            .build();
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
    public PageLayout addBlockData(
        @PathVariable String id, @PathVariable String blockId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BlockData.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "图片区块数据",
                        value = """
                        {
                            "image": "https://picsum.photos/200/300",
                            "link": {
                                "type": "URL",
                                "value": "https://www.baidu.com"
                            },
                            "title": "图片标题"
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "房源数据",
                        value = """
                        {
                            "houseId": 1,
                            "title": "房源标题",
                            "cover": "https://picsum.photos/200/300",
                            "description": "建面125-185m² | 三四房",
                            "sellingPoints": [
                                {
                                    "text": "卖点标签",
                                    "color": "#FF0000",
                                    "backgroundColor": "#FFFFFF"
                                }
                            ],
                            "promotionTags": [
                                "https://picsum.photos/32/16"
                            ],
                            "totalPrice": "100万",
                            "unitPrice": "10000元/平",
                            "recommendation": [
                                {
                                    "text": "推荐理由"
                                }
                            ]
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "分类区块数据",
                        value = """
                        {
                            "categoryCode": "categoryCode"
                        }
                        """
                    ),
                }
            )
        )
        @Valid @RequestBody BlockData createData) {
        val data = PageBlockData.builder()
            .content(createData)
            .build();
        return pageLayoutService.addBlockData(id, blockId, data);
    }

    @Operation(summary = "更新区块数据")
    @PutMapping("/{id}/blocks/{blockId}/data/{dataId}")
    public PageLayout updateBlockData(
        @PathVariable String id,
        @PathVariable String blockId,
        @PathVariable String dataId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BlockData.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "图片区块数据",
                        value = """
                        {
                            "image": "https://picsum.photos/200/300",
                            "link": {
                                "type": "URL",
                                "value": "https://www.baidu.com"
                            },
                            "title": "图片标题"
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "房源数据",
                        value = """
                        {
                            "houseId": 1,
                            "title": "房源标题",
                            "cover": "https://picsum.photos/200/300",
                            "description": "建面125-185m² | 三四房",
                            "sellingPoints": [
                                {
                                    "text": "卖点标签",
                                    "color": "#FF0000",
                                    "backgroundColor": "#FFFFFF"
                                }
                            ],
                            "promotionTags": [
                                "https://picsum.photos/32/16"
                            ],
                            "totalPrice": "100万",
                            "unitPrice": "10000元/平",
                            "recommendation": [
                                {
                                    "text": "推荐理由"
                                }
                            ]
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "分类区块数据",
                        value = """
                        {
                            "categoryCode": "categoryCode"
                        }
                        """
                    ),
                }
            )
        )
        @Valid @RequestBody BlockData updateData) {
        val data = PageBlockData.builder()
            .content(updateData)
            .build();
        return pageLayoutService.updateBlockData(id, blockId, dataId, data);
    }

    @Operation(summary = "删除区块数据")
    @DeleteMapping("/{id}/blocks/{blockId}/data/{dataId}")
    public PageLayout removeBlockData(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.removeBlockData(id, blockId, dataId);
    }

    @Operation(summary = "向上移动区块数据一位")
    @PostMapping("/{id}/blocks/{blockId}/data/{dataId}/move-up")
    public PageLayout moveBlockDataUp(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.moveBlockDataUp(id, blockId, dataId);
    }

    @Operation(summary = "向下移动区块数据一位")
    @PostMapping("/{id}/blocks/{blockId}/data/{dataId}/move-down")
    public PageLayout moveBlockDataDown(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.moveBlockDataDown(id, blockId, dataId);
    }

    @Operation(summary = "移动区块数据到顶部")
    @PostMapping("/{id}/blocks/{blockId}/data/{dataId}/move-top")
    public PageLayout moveBlockDataTop(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.moveBlockDataTop(id, blockId, dataId);
    }

    @Operation(summary = "移动区块数据到底部")
    @PostMapping("/{id}/blocks/{blockId}/data/{dataId}/move-bottom")
    public PageLayout moveBlockDataBottom(@PathVariable String id, @PathVariable String blockId, @PathVariable String dataId) {
        return pageLayoutService.moveBlockDataBottom(id, blockId, dataId);
    }
}
