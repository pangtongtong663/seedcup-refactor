package com.seedcup.seedcupbackend.global.config;

import com.google.common.base.Predicates;
import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Swagger2Config {

    @Value("${swagger.enabled}")
    private boolean enabled;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.version}")
    private String version;

    @Bean
    public Docket createRestApi() {

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(
                new ResponseMessageBuilder()
                .code(0)
                .message("success")
                .build()
        );
        responseMessageList.add(
                new ResponseMessageBuilder()
                .code(100)
                .message("fail")
                .build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        if (enabled) {
            return new ApiInfoBuilder()
                    .title(title)
                    .description(description)
                    .version(version)
                    .build();
        }
        return new ApiInfoBuilder().build();
    }
}
