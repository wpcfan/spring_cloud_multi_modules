package com.fangxiaoer.cms.converter;

import com.fangxiaoer.cms.models.*;
import com.fangxiaoer.cms.models.enumeration.ImageLinkType;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BlockDataConverter implements Converter<Document, BlockData> {
    @Override
    public BlockData convert(Document source) {
        return switch (source.getString("_class")) {
            case "house" -> HouseData.builder()
                    .houseId(source.getLong("houseId"))
                    .title(source.getString("title"))
                .cover(source.getString("cover"))
                .description(source.getString("description"))
                .totalPrice(source.getString("totalPrice"))
                .unitPrice(source.getString("unitPrice"))
                .promotionTags(source.getList("promotionTags", String.class))
                .sellingPoints(source.getList("sellingPoints", Document.class).stream()
                    .map(sellingPoint -> TextContent.builder()
                        .title(sellingPoint.getString("title"))
                        .backgroundColor(sellingPoint.getString("backgroundColor"))
                        .textColor(sellingPoint.getString("textColor"))
                        .bold(sellingPoint.getBoolean("bold"))
                        .fontFamily(sellingPoint.getString("fontFamily"))
                        .fontSize(sellingPoint.getInteger("fontSize"))
                        .textAlign(sellingPoint.getString("textAlign"))
                        .italic(sellingPoint.getBoolean("italic"))
                        .build())
                    .toList())
                .recommendation(source.getList("recommendation", Document.class).stream()
                    .map(recommendation -> TextContent.builder()
                        .title(recommendation.getString("title"))
                        .backgroundColor(recommendation.getString("backgroundColor"))
                        .textColor(recommendation.getString("textColor"))
                        .bold(recommendation.getBoolean("bold"))
                        .fontFamily(recommendation.getString("fontFamily"))
                        .fontSize(recommendation.getInteger("fontSize"))
                        .textAlign(recommendation.getString("textAlign"))
                        .italic(recommendation.getBoolean("italic"))
                        .build())
                    .toList())
                    .build();
            case "image"  -> ImageData.builder()
                    .image(source.getString("image"))
                    .link(ImageLink.builder()
                        .value(source.getString("link.value"))
                        .type(source.getString("link.type") == null ? null : ImageLinkType.valueOf(source.getString("link.type")))
                        .build())
                    .title(source.getString("title"))
                    .build();
            case "category" -> CategoryData.builder()
                    .categoryCode(source.getString("categoryCode"))
                    .build();

            default -> throw new IllegalArgumentException("Unknown class type");
        };
    }
}
