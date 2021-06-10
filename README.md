# Описание

Imix WEB service new, версия CMS реализованная на Spring Boot. 

# Сборка и тестирование

Команда сборки приложения:

```
mvn clean package
```

После успешной сборки _jar_ приложения находится в файле _target/application.jar_:
 
* Порт по умолчанию - 80
* Интерфейс Swagger доступен по адресу _http://localhost/swagger-ui/_ (swagger-ui/index.html)
* Путь Actuator API - _http://localhost/actuator/_
* Путь Prometheus API - _http://localhost/actuator/prometheus_

Команда сборки Docker-образа с приложением:

```
mvn clean package -Pdocker
```

Если вы хотите использовать Vault как конфигурационный сервер необходимо указать параметры соединения с сервером _Vault_. 
Перед стартом передать переменные окружения:

```
export \
  SPRING_CLOUD_VAULT_URI=https://vault-dev.drksis.imix.by/ \
  SPRING_CLOUD_VAULT_TOKEN=ваш токен \
  SPRING_CLOUD_VAULT_KV_BACKEND=imix_dev
  
  
docker run --rm -it \
  -e SPRING_CLOUD_VAULT_URI \
  -e SPRING_CLOUD_VAULT_TOKEN \
  -e SPRING_CLOUD_VAULT_KV_BACKEND \
  -p 80:80 \
  imix/prototypes/rest-service-prototype:1.0.0-SNAPSHOT
``` 
## Что в шаблоне?

Основной код:

* [Application.java](src/main/java/by/imix/project/proto/Application.java) - основной класс приложения Spring Boot
* [ApplicationConfiguration.java](src/main/java/by/imix/project/proto/ApplicationConfiguration.java) - пример Java-based конфигурации приложения
* [PrototypeService.java](/src/main/java/by/imix/project/proto/services/PrototypeService.java) - пример Spring-сервиса, который выполняет арифметические вычисления
* [PrototypeController.java](src/main/java/by/imix/project/proto/controllers/PrototypeController.java) - пример REST-контроллера, который делегирует вызовы сервису
* [ExceptionController.java](src/main/java/by/imix/project/proto/controllers/ExceptionController.java) - пример обработчика исключений Spring Boot Rest
* [bootstrap.yml](src/main/resources/application.yml) - первичные загрузочные параметры приложения и значения по умолчанию
* [logback-spring.xml](src/main/resources/logback-spring.xml) - профили с настройками логирования

Тесты:

* [TestPrototypeService.java](src/test/java/by/imix/project/proto/services/TestPrototypeService.java) - пример модульного теста, который проверяет Spring-сервис
* [TestPrototypeController.java](/src/test/java/by/imix/project/proto/controllers/TestPrototypeController.java) - пример интеграционного теста, который проверяет API контроллера
* [JupiterBase.java](src/test/java/by/imix/project/proto/JupiterBase.java) - конфигурация тестового окружения Spring Boot и логирование тестов
* [application-test.yml](src/test/resources/application-test.yml) - параметры конфигурации тестов
* [logback-test.xml](src/test/resources/logback-test.xml) - настройки логирования при выполнении тестов

## Какие пакеты использем?

* [spring-boot-starter-web](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-developing-web-applications) - стартер для WEB-приложений, подключает все необходимое для создания REST-сервиса
* [springfox-boot-starter](https://springfox.github.io/springfox/docs/current/) - компонент на основе Swagger, который генерирует API-документацию
* [spring-boot-starter-logging](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-logging) и [spring-cloud-starter-sleuth](https://spring.io/projects/spring-cloud-sleuth) - компоненты расширенного логирования и трассировки
* [spring-boot-starter-actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready) и [micrometer-registry-prometheus](https://micrometer.io/docs/registry/prometheus) - сервисы диагностики и интеграция с Prometheus 
* [spring-cloud-starter-vault-config](https://cloud.spring.io/spring-cloud-vault/reference/html/) - интеграция с сервером [Vault by HashiCorp](https://www.vaultproject.io/)
* [spring-cloud-starter-netflix-eureka-client](https://cloud.spring.io/spring-cloud-netflix/reference/html/) - интеграция с [Eureka от Netflix](https://github.com/Netflix/eureka/wiki)
* [spring-boot-configuration-processor](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-configuration-metadata.html) - генератор метаданных для классов `@ConfigurationProperties`
* [junit-jupiter-engine, junit-platform-launcher, junit-vintage-engine](https://junit.org/junit5/docs/current/user-guide/) и [assertj-core](https://assertj.github.io/doc/) - средства модульного тестирования
* [spring-boot-starter-test](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing) - расширение Spring и Jupiter для модульного и интеграционного тестирования


