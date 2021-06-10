package by.imix.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.util.PropertyUtils;

import java.util.Locale;

/**
 * Основной класс приложения Spring Boot.
 * <p>
 * В этом класс помещаем только корневые и системнве параметры. Вся конфигурация приложения содержится в классе
 * {@link ApplicationConfiguration}.
 *
 * @see ApplicationConfiguration
 */
//ConfigurationProperties(prefix = "cust.data.employee")
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })1
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {

        // Стартуем с русской локализацией:
        Locale.setDefault(new Locale("en"));

        // Программно включаем Spring Cloud Bootstrap.
        // Если уверен, что Bootstrap нужен всегда, в том числе и в тестах, то можно заменить эту строку
        // на зависимость "org.springframework.cloud: spring-cloud-starter-bootstrap":
//        System.setProperty(PropertyUtils.BOOTSTRAP_ENABLED_PROPERTY, Boolean.toString(true));

        // Запускаем Spring:
        SpringApplication.run(Application.class, args);
    }
}