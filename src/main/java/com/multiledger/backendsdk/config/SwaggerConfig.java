package com.multiledger.backendsdk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.multiledger.backendsdk.repository.UserRepository;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.multiledger.backendsdk"))
                .paths(regex("/fabric.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "Hyperledger Fabric Backend REST API",
                "fabeic backend API for multiledger inc.",
                "1.0",
                "Terms of service",
                new Contact("Multiledger Inc.", "www.multiledger.io", "xxx@multiledger.io"),
                "Apache License Version 2.0",
                "https://www.apache.org/licensen.html", Collections.emptyList());
    }

}