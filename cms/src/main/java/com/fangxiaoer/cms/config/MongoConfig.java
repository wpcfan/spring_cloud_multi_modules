package com.fangxiaoer.cms.config;

import com.fangxiaoer.cms.converter.BlockDataConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "com.fangxiaoer.cms.repositories")
public class MongoConfig {
    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(new BlockDataConverter()));
    }
}
