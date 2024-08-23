package com.fangxiaoer.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fangxiaoer.common.error.EnableGlobalExceptionHandling;

@EnableGlobalExceptionHandling
@SpringBootApplication
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
