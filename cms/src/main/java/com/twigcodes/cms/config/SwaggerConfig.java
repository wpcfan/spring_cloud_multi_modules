package com.twigcodes.cms.config;

import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class SwaggerConfig {
    @Bean
    public ModelConverter localDateTimeModelConverter() {
        return (type, context, chain) -> {
            if (TypeFactory.defaultInstance().constructType(LocalDateTime.class).equals(type.getType())) {
                Schema<?> schema = new Schema<>();
                schema.setType("string");
                schema.setFormat("date-time");
                schema.setExample("2024-08-20 14:30:00");
                return schema;
            }
            return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
        };
    }
}
