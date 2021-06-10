package by.imix.cms.endpoint;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@Data
@ConfigurationProperties(EndpointProperties.NAME)
public class EndpointProperties {
    public static final String NAME = "application.endpoint";

    @NonNull
    URL externalURL;

    @NonNull
    String externalHostName;
}
