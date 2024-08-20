package com.twigcodes.cms.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HouseData implements BlockData {
    private Long houseId;
    private String title;
    private String description;
    private List<TextContent> sellingPoints;
    private List<String> promotionTags;
    private String totalPrice;
    private String unitPrice;
}
