package com.twigcodes.cms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.enumeration.Platform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

@Schema(description = "页面布局")
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
@With
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "fxr_page_layouts")
public class PageLayout implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Builder.Default
    @Schema(description = "页面ID")
    private String id = UUID.randomUUID().toString();
    @Schema(description = "页面标题")
    private String title;
    @Schema(description = "页面配置")
    private PageConfig config;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "布局生效时间", format = "date-time", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "布局失效时间", format = "date-time", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @Schema(description = "页面状态")
    @Builder.Default
    private PageStatus status = PageStatus.DRAFT;
    @Schema(description = "目标页面")
    @Builder.Default
    private TargetPage targetPage = TargetPage.HOME;
    @Schema(description = "平台")
    @Builder.Default
    private Platform platform = Platform.APP;
    @Schema(description = "区块集合")
    @Builder.Default
    private SortedSet<PageBlock> blocks = new TreeSet<>();

    public void moveBlockUp(String blockId) {
        PageBlock block = blocks.stream().filter(b -> b.getId().equals(blockId)).findFirst().orElseThrow();
        PageBlock prevBlock = blocks.stream().filter(b -> b.getSort() < block.getSort()).max(PageBlock::compareTo).orElse(null);
        if (prevBlock != null) {
            int sort = block.getSort();
            block.setSort(prevBlock.getSort());
            prevBlock.setSort(sort);
        }
    }

    public void moveBlockDown(String blockId) {
        PageBlock block = blocks.stream().filter(b -> b.getId().equals(blockId)).findFirst().orElseThrow();
        PageBlock nextBlock = blocks.stream().filter(b -> b.getSort() > block.getSort()).min(PageBlock::compareTo).orElse(null);
        if (nextBlock != null) {
            int sort = block.getSort();
            block.setSort(nextBlock.getSort());
            nextBlock.setSort(sort);
        }
    }

    public void moveBlockTop(String blockId) {
        PageBlock block = blocks.stream().filter(b -> b.getId().equals(blockId)).findFirst().orElseThrow();
        int sort = block.getSort();
        block.setSort(blocks.first().getSort() - 1);
        blocks.stream().filter(b -> b.getSort() < sort).forEach(b -> b.setSort(b.getSort() + 1));
    }

    public void moveBlockBottom(String blockId) {
        PageBlock block = blocks.stream().filter(b -> b.getId().equals(blockId)).findFirst().orElseThrow();
        int sort = block.getSort();
        block.setSort(blocks.last().getSort() + 1);
        blocks.stream().filter(b -> b.getSort() > sort).forEach(b -> b.setSort(b.getSort() - 1));
    }
}
