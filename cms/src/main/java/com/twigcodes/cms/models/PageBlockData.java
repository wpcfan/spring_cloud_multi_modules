package com.twigcodes.cms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Schema(description = "区块数据")
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageBlockData implements Comparable<PageBlockData>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Builder.Default
    @Schema(description = "区块数据ID")
    private String id = UUID.randomUUID().toString();
    @Schema(description = "排序")
    private Integer sort;
    @Schema(description = "区块数据")
    private BlockData content;

    @Override
    public int compareTo(PageBlockData o) {
        return this.sort.compareTo(o.sort);
    }
}

