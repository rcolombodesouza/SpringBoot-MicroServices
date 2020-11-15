package com.store.core.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the JSON Web Token.
 *
 * @author rafaelcolombodesouza
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@Getter
@Setter
@ToString
public class JwtConfiguration {

    @NestedConfigurationProperty
    private Header header = new Header();
    private String loginUrl = "/login/**";
    private int expiration = 3600;
    private String privateKey = "mM97YrQsD6NSoI9BTMheKCG9olfFg8ib";
    private String type = "encrypted";

    @Getter
    @Setter
    public static class Header {
        private String name = "Authorization";
        private String prefix = "Bearer ";
    }
}
