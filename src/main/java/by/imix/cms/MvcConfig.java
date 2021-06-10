package by.imix.cms;

import by.imix.cms.model.DynamicContentService;
import by.imix.cms.web.dynamiccontent.DynamicContentHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MvcConfig  {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper() //
                .registerModule(new JavaTimeModule() //
                        .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE)) //
                        .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_DATE)) //
                        .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_TIME)) //
                        .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_TIME)) //
                        .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)) //
                        .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME)) //
                )//
                .enable( //
                        DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, //
                        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, //
                        DeserializationFeature.READ_ENUMS_USING_TO_STRING //
                ) //
                .disable( //
                        SerializationFeature.FAIL_ON_EMPTY_BEANS, //
                        SerializationFeature.INDENT_OUTPUT, //
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS //
                ) //
                .disable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) //
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

//    @Autowired
//    DynamicContentHandler dynamicContentHandler;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(dynamicContentHandler);
//    }

//    @Bean
//    DynamicContentHandler dynamicContentHandler(DynamicContentService dynamicContentService) {
//        return new DynamicContentHandler(dynamicContentService);
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/resources/resources/**")
//                .addResourceLocations("/resources/resources/");
//    }
//
//
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.getClass();
//    }
@Bean
public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setFallbackToSystemLocale(false);
    messageSource.setBasename("classpath*:/i18n/MessageResources");
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}
}
