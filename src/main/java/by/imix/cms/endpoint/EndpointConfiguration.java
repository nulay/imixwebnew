package by.imix.cms.endpoint;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

//zipkin config for tracing requests beatween service
@EnableConfigurationProperties(EndpointProperties.class)
//@Configuration
public class EndpointConfiguration {
}
