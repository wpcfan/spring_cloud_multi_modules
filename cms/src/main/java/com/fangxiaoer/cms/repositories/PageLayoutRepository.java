package com.fangxiaoer.cms.repositories;

import com.fangxiaoer.cms.models.PageLayout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageLayoutRepository extends MongoRepository<PageLayout, String>, PageLayoutCustomRepository {
}
