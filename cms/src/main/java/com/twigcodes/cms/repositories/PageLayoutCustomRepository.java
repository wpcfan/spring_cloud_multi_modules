package com.twigcodes.cms.repositories;

import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PageLayoutCustomRepository {
    Page<PageLayout> findByCriteria(QueryPageLayout queryPageLayout, Pageable pageable);
    List<PageLayout> findAllByCriteria(TargetPage targetPage, Platform platform, LocalDateTime now);
}
