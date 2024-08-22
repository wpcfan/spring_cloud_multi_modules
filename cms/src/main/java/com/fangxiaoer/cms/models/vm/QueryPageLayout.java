package com.fangxiaoer.cms.models.vm;

import com.fangxiaoer.cms.models.enumeration.BlockType;
import com.fangxiaoer.cms.models.enumeration.PageStatus;
import com.fangxiaoer.cms.models.enumeration.Platform;
import com.fangxiaoer.cms.models.enumeration.TargetPage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Jacksonized
@Getter
@Setter
@Builder
@With
public class QueryPageLayout {
    private String title;
    private LocalDateTime startTimeFrom;
    private LocalDateTime startTimeTo;
    private LocalDateTime endTimeFrom;
    private LocalDateTime endTimeTo;
    private PageStatus status;
    private TargetPage targetPage;
    private Platform platform;
    private BlockType blockType;
}
