package com.twigcodes.cms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "房源数据", example = """
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
""")
@JsonDeserialize(as = HouseData.class)
@Getter
@Setter
@Builder
public class HouseData implements BlockData {
    @Schema(description = "房源 ID", example = "1")
    private Long houseId;
    @Schema(description = "房源标题", example = "房源标题")
    private String title;
    @Schema(description = "房源封面", example = "https://picsum.photos/200/300")
    private String cover;
    @Schema(description = "房源描述", example = "建面125-185m² | 三四房")
    private String description;
    @Schema(description = "房源卖点标签", example = "[{\"text\":\"卖点标签\",\"color\":\"#FF0000\",\"backgroundColor\":\"#FFFFFF\"}]")
    private List<TextContent> sellingPoints;
    @Schema(description = "房源促销标签，图标 URL", example = "[\"https://picsum.photos/32/16\"]")
    private List<String> promotionTags;
    @Schema(description = "房源总价", example = "100万")
    private String totalPrice;
    @Schema(description = "房源单价", example = "10000元/平")
    private String unitPrice;
    @Schema(description = "房源推荐理由", example = "[{\"text\":\"推荐理由\"}]")
    private List<TextContent> recommendation;
}
