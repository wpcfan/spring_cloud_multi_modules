package com.fangxiaoer.cms.repositories;

import com.fangxiaoer.cms.models.PageLayout;
import com.fangxiaoer.cms.models.enumeration.Platform;
import com.fangxiaoer.cms.models.enumeration.TargetPage;
import com.fangxiaoer.cms.models.vm.QueryPageLayout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PageLayoutCustomRepository {
    Page<PageLayout> findByCriteria(QueryPageLayout queryPageLayout, Pageable pageable);
    List<PageLayout> findAllByCriteria(TargetPage targetPage, Platform platform, LocalDateTime now);
}
