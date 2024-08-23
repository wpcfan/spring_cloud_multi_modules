package com.fangxiaoer.cms.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;

@Schema(description = "图片区块数据", example = """
{
    "image": "https://picsum.photos/200/300",
    "link": {
        "type": "url",
        "value": "https://www.baidu.com"
    },
    "title": "图片标题"
}
""")
@JsonDeserialize(as = ImageData.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("image")
public class ImageData implements BlockData {
    @Schema(description = "图片地址", example = "https://picsum.photos/200/300")
    private String image;
    @Schema(description = "图片链接", example = "https://www.baidu.com")
    private ImageLink link;
    @Schema(description = "图片标题", example = "图片标题")
    private String title;
}
