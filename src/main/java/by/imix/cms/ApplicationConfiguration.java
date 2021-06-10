package by.imix.cms;

import by.imix.cms.nodedata.NodeStateReaderJSON;
import by.imix.cms.nodedata.NodeStateReaderService;
import by.imix.cms.nodedata.service.NodeService;
import by.imix.cms.nodedata.service.StateService;
import by.imix.cms.nodedata.service.hib.NodeServiceImpl;
import by.imix.cms.web.dynamiccontent.DynamicContentHandler;
import by.imix.cms.web.image.FileUploader;
import by.imix.cms.web.image.FileUploaderIface;
import by.imix.cms.web.security.CredentialBox;
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
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * Класс с Java-based конфигурацией приложения.
 * <p>
 * Для удобства конфигурация разделена на блоки по компонентам, которые мы используем или реализуем (например,
 * {@link OpenApiConfiguration}). Внешние конфигурационные блоки можно подключать аннотацией {@link Import} или простым
 * наследованием.
 * <p>
 * Мы используем {@link EnableDiscoveryClient} для интеграции с Eureka. По умолчанию клиент Eureka отключен в файле
 * {@code bootstrap.yml}. Его можно включить, если в Vault определить конфигурационный параметр {@code eureka.client.enabled} в
 * значение {@code true} и задать адрес Eureka, {@code eureka.client.serviceUrl.defaultZone}.
 */
//@EnableDiscoveryClient
@Configuration

@ComponentScan(basePackages = "by.imix")
@EnableTransactionManagement
//@EnableRetry
public class ApplicationConfiguration { //extends EndpointConfiguration


    @Bean(value = "imageUploadForAvatar")
    public FileUploaderIface imageUploadForAvatar() {
        FileUploaderIface imageUploadForAvatar = new FileUploader();
        imageUploadForAvatar.setFormat(new String[]{"image/jpeg", "image/png", "image/gif"});
        //максимальный размер 500кб
        imageUploadForAvatar.setMaxSize(512000);
        return imageUploadForAvatar;
    }

    @Bean(value = "imageUploadForUser")
    public FileUploaderIface imageUploadForUser() {
        FileUploaderIface imageUploadForAvatar = new FileUploader();
        imageUploadForAvatar.setFormat(new String[]{"image/jpeg", "image/png", "image/gif", "application/pdf", "application/msword"});
        //максимальный размер 3 мб
        imageUploadForAvatar.setMaxSize(3145728);
        return imageUploadForAvatar;
    }

    @Bean
    public NodeService nodeServiceImpl() {
        return new NodeServiceImpl();
    }


    @Bean
    public NodeStateReaderJSON nodeStateReaderJSON(NodeService nodeServiceImpl, StateService stateService) {
        NodeStateReaderJSON nodeStateReaderJSON = new NodeStateReaderJSON();
        nodeStateReaderJSON.setNodeService(nodeServiceImpl);
        nodeStateReaderJSON.setStateService(stateService);
        NodeStateReaderService.setNodeStateReader(nodeStateReaderJSON);
        return nodeStateReaderJSON;
    }

    @Bean
    public Set<String> listCredentialCore() {
        return Set.of("ROLE_AUTH_DATA", "ROLE_ADMIN_SETTINGS");
    }

    @Bean
    public Set<String> listCredentialDynContent() {
        return Set.of("ROLE_DYNCONT_CRUD");
    }

    @Bean
    public CredentialBox credentialBox(Set<String> listCredentialCore, Set<String> listCredentialDynContent) {
        CredentialBox credentialBox = CredentialBox.getInstance();
        credentialBox.addAll(listCredentialCore);
        credentialBox.addAll(listCredentialDynContent);
        return CredentialBox.getInstance();
    }
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory cmsSessionFactory) {
//        HibernateTransactionManager transactionManager
//                = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(
//                cmsSessionFactory);
//
//        return transactionManager;
//    }
    @Bean
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory);

        return transactionManager;
    }
//    @Bean
//    public SessionFactory sessionFactory() {
//        return new AnnotationSessionFactoryBuilder()
//                .setDataSource(dataSource())
//                .setPackagesToScan("com.myco")
//                .buildSessionFactory();
//    }
}