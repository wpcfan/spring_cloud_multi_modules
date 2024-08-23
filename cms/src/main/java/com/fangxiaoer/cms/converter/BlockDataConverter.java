package com.fangxiaoer.cms.converter;

import com.fangxiaoer.cms.models.BlockData;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BlockDataConverter implements Converter<Document, BlockData> {
    @Override
    public BlockData convert(Document source) {
        return switch (source.getString("_class")) {
            case "com.fangxiaoer.cms.models.HouseData" -> null;
            case "com.fangxiaoer.cms.models.ImageData" -> null;
            case "com.fangxiaoer.cms.models.CategoryData" -> null;
            default -> throw new IllegalArgumentException("Unknown class type");
        };
    }
}
