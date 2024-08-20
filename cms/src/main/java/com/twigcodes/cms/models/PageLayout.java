package com.twigcodes.cms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.enumeration.Platform;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
@With
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "fxr_page_layouts")
public class PageLayout implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String title;
    private PageConfig config;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Builder.Default
    private PageStatus status = PageStatus.DRAFT;
    @Builder.Default
    private TargetPage targetPage = TargetPage.Home;
    @Builder.Default
    private Platform platform = Platform.APP;
    private List<PageBlock> blocks;
}
