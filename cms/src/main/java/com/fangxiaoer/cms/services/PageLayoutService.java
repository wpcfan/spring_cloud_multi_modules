package com.fangxiaoer.cms.services;

import com.fangxiaoer.cms.models.PageLayout;
import com.fangxiaoer.cms.models.enumeration.Platform;
import com.fangxiaoer.cms.models.enumeration.TargetPage;
import com.fangxiaoer.cms.repositories.PageLayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PageLayoutService {
    private final PageLayoutRepository pageLayoutRepository;

    public PageLayout get(TargetPage targetPage, Platform platform, LocalDateTime now) {
        return pageLayoutRepository.findAllByCriteria(targetPage, platform, now)
            .stream().findFirst().orElseGet(this::buildDefaultPageLayout);
    }

    private PageLayout buildDefaultPageLayout() {
        return PageLayout.builder()
            .title("Default")
            .build();
    }

}
