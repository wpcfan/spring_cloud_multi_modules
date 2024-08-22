package com.fangxiaoer.cms.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fangxiaoer.cms.models.BlockData;
import com.fangxiaoer.cms.models.CategoryData;
import com.fangxiaoer.cms.models.HouseData;
import com.fangxiaoer.cms.models.ImageData;

import java.io.IOException;

public class BlockDataDeserializer extends JsonDeserializer<BlockData> {

    @Override
    public BlockData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ObjectNode root = mapper.readTree(p);
        if (root.has("houseId")) {
            return mapper.readValue(root.toString(), HouseData.class);
        }
        if (root.has("image")) {
            return mapper.readValue(root.toString(), ImageData.class);
        }
        if (root.has("categoryCode")) {
            return mapper.readValue(root.toString(), CategoryData.class);
        }
        return null;
    }
}
