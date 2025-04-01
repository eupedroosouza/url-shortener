package io.github.eupedroosouza.urlshortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {

    private Duration timeToExpire;
    private String baseUrl;

}
