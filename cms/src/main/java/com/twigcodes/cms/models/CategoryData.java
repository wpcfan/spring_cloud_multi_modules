package com.twigcodes.cms.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "分类区块数据")
@JsonDeserialize(as = ImageData.class)
@Builder
@Getter
@Setter
public class CategoryData implements BlockData {
    private String categoryCode;
}
