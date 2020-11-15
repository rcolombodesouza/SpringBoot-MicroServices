package com.store.book.security.config;

import com.store.core.property.JwtConfiguration;
import com.store.security.config.TokenSecurityConfig;
import com.store.security.filter.JwtTokenFilter;
import com.store.security.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configure the user credentials.
 *
 * @author rafaelcolombodesouza
 */
@EnableWebSecurity
public class BookSecurityConfig extends TokenSecurityConfig {

    private final TokenConverter tokenConverter;

    /**
     * Constructor
     * @param jwtConfiguration Configuration class for the JSON Web Token
     * @param tokenConverter object to convert a token
     */
    public BookSecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    /**
     * Configure the http security
     * @param http object to be configured
     * @throws Exception in case it fails to configure
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new JwtTokenFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}
