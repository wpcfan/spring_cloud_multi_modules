package com.twigcodes.cms.services;

import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import com.twigcodes.cms.repositories.PageLayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PageLayoutAdminService {
    private final PageLayoutRepository pageLayoutRepository;

    public void delete(String id) {
        pageLayoutRepository.deleteById(id);
    }

    public PageLayout update(String id, PageLayout pageLayout) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setTitle(pageLayout.getTitle());
                existingPageLayout.setStartTime(pageLayout.getStartTime());
                existingPageLayout.setEndTime(pageLayout.getEndTime());
                existingPageLayout.setStatus(pageLayout.getStatus());
                existingPageLayout.setTargetPage(pageLayout.getTargetPage());
                existingPageLayout.setPlatform(pageLayout.getPlatform());
                if (pageLayout.getBlocks() != null && !pageLayout.getBlocks().isEmpty()) {
                    existingPageLayout.setBlocks(pageLayout.getBlocks());
                }
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout publish(String id, LocalDateTime startTime, LocalDateTime endTime) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setStatus(PageStatus.PUBLISHED);
                existingPageLayout.setStartTime(startTime);
                existingPageLayout.setEndTime(endTime);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout draft(String id) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setStatus(PageStatus.DRAFT);
                existingPageLayout.setStartTime(null);
                existingPageLayout.setEndTime(null);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout create(PageLayout pageLayout) {
        return pageLayoutRepository.save(pageLayout);
    }

    public Page<PageLayout> searchPageLayouts(QueryPageLayout queryPageLayout, Pageable pageable) {
        return pageLayoutRepository.findByCriteria(queryPageLayout, pageable);
    }
}
