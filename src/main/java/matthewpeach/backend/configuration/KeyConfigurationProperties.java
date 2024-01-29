package matthewpeach.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("apikey")
public record KeyConfigurationProperties(String GOOGLE_MAP_KEY) {
}
