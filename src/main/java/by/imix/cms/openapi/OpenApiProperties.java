package by.imix.cms.openapi;

import by.imix.cms.Defs;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import java.util.List;

@Data
@Validated
@ConfigurationProperties(OpenApiProperties.NAME)
public class OpenApiProperties {
    public static final String NAME = "application.api";

    public static final String DEFAULT_VERSION = "develop";
    public static final String DEFAULT_CONTACT_NAME = "Команда Imix";
    public static final String DEFAULT_CONTACT_URL = "https://imix.by";
    public static final String DEFAULT_CONTACT_EMAIL = "nulay@mail.ru";

    @NotBlank
    String title;
    @NotBlank
    String description;
    @NotBlank
    String version = DEFAULT_VERSION;
    @NotBlank
    String contactName = DEFAULT_CONTACT_NAME;
    @NotBlank
    String contactUrl = DEFAULT_CONTACT_URL;
    @NotBlank
    @Pattern(regexp = Defs.EMAIL_REGEX, flags = Flag.CASE_INSENSITIVE)
    String contactEmail = DEFAULT_CONTACT_EMAIL;
    List<String> apiBasePackages;
}
