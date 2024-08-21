package com.twigcodes.cms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.enumeration.Platform;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

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
    private String id = UUID.randomUUID().toString();
    private String title;
    private PageConfig config;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Builder.Default
    private PageStatus status = PageStatus.DRAFT;
    @Builder.Default
    private TargetPage targetPage = TargetPage.Home;
    @Builder.Default
    private Platform platform = Platform.APP;
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
