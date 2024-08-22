package com.twigcodes.cms.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "分类区块数据", example = """
{
    "categoryCode": "categoryCode"
}
""")
@JsonDeserialize(as = CategoryData.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryData implements BlockData {
    private String categoryCode;
}
