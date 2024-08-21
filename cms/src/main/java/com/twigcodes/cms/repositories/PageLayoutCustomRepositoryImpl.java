package com.twigcodes.cms.repositories;

import com.twigcodes.cms.models.PageLayout;
import com.twigcodes.cms.models.enumeration.PageStatus;
import com.twigcodes.cms.models.enumeration.Platform;
import com.twigcodes.cms.models.enumeration.TargetPage;
import com.twigcodes.cms.models.vm.QueryPageLayout;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PageLayoutCustomRepositoryImpl implements PageLayoutCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<PageLayout> findByCriteria(QueryPageLayout queryPageLayout, Pageable pageable) {
        Query query = buildQuery(queryPageLayout);

        // Set pagination information
        query.with(pageable);

        List<PageLayout> pageLayouts = mongoTemplate.find(query, PageLayout.class);
        val count = mongoTemplate.count(query, PageLayout.class);

        return new PageImpl<>(pageLayouts, pageable, count);
    }

    @Override
    public List<PageLayout> findAllByCriteria(TargetPage targetPage, Platform platform, LocalDateTime now) {
        Query query = new Query();
        query.addCriteria(Criteria.where("targetPage").is(targetPage));
        query.addCriteria(Criteria.where("platform").is(platform));
        query.addCriteria(Criteria.where("startTime").lte(now));
        query.addCriteria(Criteria.where("endTime").gte(now));
        query.addCriteria(Criteria.where("status").is(PageStatus.PUBLISHED));

        return mongoTemplate.find(query, PageLayout.class);
    }


    @NotNull
    private static Query buildQuery(QueryPageLayout queryPageLayout) {
        Query query = new Query();

        if (queryPageLayout.getTitle() != null && !queryPageLayout.getTitle().isEmpty()) {
            query.addCriteria(Criteria.where("title").is(queryPageLayout.getTitle()));
        }
        if (queryPageLayout.getStartTimeFrom() != null) {
            query.addCriteria(Criteria.where("startTime").gte(queryPageLayout.getStartTimeFrom()));
        }
        if (queryPageLayout.getStartTimeTo() != null) {
            query.addCriteria(Criteria.where("startTime").lte(queryPageLayout.getStartTimeTo()));
        }
        if (queryPageLayout.getEndTimeFrom() != null) {
            query.addCriteria(Criteria.where("endTime").gte(queryPageLayout.getEndTimeFrom()));
        }
        if (queryPageLayout.getEndTimeTo() != null) {
            query.addCriteria(Criteria.where("endTime").lte(queryPageLayout.getEndTimeTo()));
        }
        if (queryPageLayout.getStatus() != null) {
            query.addCriteria(Criteria.where("status").is(queryPageLayout.getStatus()));
        }
        if (queryPageLayout.getTargetPage() != null) {
            query.addCriteria(Criteria.where("targetPage").is(queryPageLayout.getTargetPage()));
        }
        if (queryPageLayout.getPlatform() != null) {
            query.addCriteria(Criteria.where("platform").is(queryPageLayout.getPlatform()));
        }
        if (queryPageLayout.getBlockType() != null) {
            query.addCriteria(Criteria.where("blocks.type").is(queryPageLayout.getBlockType()));
        }
        return query;
    }
}
