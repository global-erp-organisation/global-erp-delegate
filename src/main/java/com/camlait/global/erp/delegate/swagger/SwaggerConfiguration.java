package com.camlait.global.erp.delegate.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Sets;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
@Configuration
public class SwaggerConfiguration {

    @Autowired
    private SwaggerProperties propertie;
    
    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().pathMapping("/")
                .protocols(Sets.newHashSet("http", "https")).apiInfo(apiInfo());

    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfiguration.DEFAULT;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(propertie.getTitle())
                .description(propertie.getDescription())
                .termsOfServiceUrl(propertie.getTermOfUseUrl())
                .contact(new Contact(propertie.getContactName(), propertie.getContactUrl(), propertie.getContactEmail()))
                .license(propertie.getLicence())
                .licenseUrl(propertie.getLicenceUrl())
                .version(propertie.getVersion())
                .build();
    }

 }
