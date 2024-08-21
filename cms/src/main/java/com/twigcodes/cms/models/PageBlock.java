package com.twigcodes.cms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twigcodes.cms.models.enumeration.BlockType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

@Schema(description = "页面区块")
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageBlock implements Comparable<PageBlock>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String title;
    private BlockType type;
    private Integer sort;
    private BlockConfig config;
    @Builder.Default
    private SortedSet<PageBlockData> data = new TreeSet<>();
    @Override
    public int compareTo(PageBlock o) {
        return this.sort.compareTo(o.sort);
    }
}

