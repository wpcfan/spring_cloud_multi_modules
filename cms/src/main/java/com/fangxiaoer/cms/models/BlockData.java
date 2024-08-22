package com.fangxiaoer.cms.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fangxiaoer.cms.deserializer.BlockDataDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(
        description = "区块数据，包含图片、分类、房源等",
        subTypes = {ImageData.class, CategoryData.class, HouseData.class},
        oneOf = {ImageData.class, CategoryData.class, HouseData.class}
)
@JsonDeserialize(using = BlockDataDeserializer.class)
public interface BlockData extends Serializable {
}
