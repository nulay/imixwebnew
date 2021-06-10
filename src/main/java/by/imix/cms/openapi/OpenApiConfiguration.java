package by.imix.cms.openapi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@EnableConfigurationProperties(OpenApiProperties.class)
@Configuration
public class OpenApiConfiguration {
    @Bean
    public Docket api(OpenApiProperties apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2) //
                .select() //
                .apis(name(apiInfo)) //
                .paths(PathSelectors.any()) //
                .build() //
                .useDefaultResponseMessages(false) //
                .apiInfo(//
                        new ApiInfo( //
                                apiInfo.getTitle(), //
                                apiInfo.getDescription(), //
                                apiInfo.getVersion(), //
                                null, //
                                new Contact(apiInfo.getContactName(), apiInfo.getContactUrl(), apiInfo.getContactEmail()), //
                                null, //
                                null, //
                                List.of()));
    }

    protected Predicate<RequestHandler> name(OpenApiProperties apiInfo) {
        return Optional.ofNullable(apiInfo.getApiBasePackages()) //
                .flatMap(basePackages -> basePackages.stream() //
                        .map(RequestHandlerSelectors::basePackage) //
                        .reduce(Predicate::or)) //
                .orElseGet(() -> ApiSelector.DEFAULT.getRequestHandlerSelector());
    }
}