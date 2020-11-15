package com.store.gateway.security.config;

import com.store.core.property.JwtConfiguration;
import com.store.gateway.security.filter.JwtGatewayFilter;
import com.store.security.config.TokenSecurityConfig;
import com.store.security.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * It is necessary to know what is valid/invalid.
 *
 * @author rafaelcolombodesouza
 */
@EnableWebSecurity
public class GatewaySecurityConfig extends TokenSecurityConfig {

    private final TokenConverter tokenConverter;

    /**
     * Constructor.
     * @param jwtConfiguration JSON Web Token configuration.
     * @param tokenConverter converter for the token.
     */
    public GatewaySecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    /**
     * Configure the http object.
     * @param http object to be configured.
     * @throws Exception in case it is not possible to configure the http.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new JwtGatewayFilter(jwtConfiguration, tokenConverter),
                UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}
