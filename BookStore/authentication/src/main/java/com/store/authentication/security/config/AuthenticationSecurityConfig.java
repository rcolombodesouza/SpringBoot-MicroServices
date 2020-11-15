package com.store.authentication.security.config;

import com.store.authentication.security.filter.JwtAuthenticationFilter;
import com.store.core.property.JwtConfiguration;
import com.store.security.config.TokenSecurityConfig;
import com.store.security.filter.JwtTokenFilter;
import com.store.security.token.converter.TokenConverter;
import com.store.security.token.creator.TokenCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configure the user credentials.
 *
 * @author rafaelcolombodesouza
 */
@EnableWebSecurity
public class AuthenticationSecurityConfig extends TokenSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final TokenCreator tokenCreator;
    private final TokenConverter tokenConverter;

    /**
     * Constructor.
     * @param jwtConfiguration Configuration class for the JSON Web Token.
     * @param userDetailsService User information.
     * @param tokenCreator object to generate a token.
     * @param tokenConverter object to convert a token.
     */
    public AuthenticationSecurityConfig(JwtConfiguration jwtConfiguration,
                                        @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                        TokenCreator tokenCreator, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
        this.tokenConverter = tokenConverter;
    }

    /**
     * Configure the http security.
     * @param http object to be configured.
     * @throws Exception in case it fails to configure.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenCreator))
        .addFilterAfter(new JwtTokenFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

    /**
     * Configure the authentication builder.
     * @param auth object to be configured.
     * @throws Exception in case it fails to configure.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Give a bean of BCryptPasswordEncoder type.
     * @return a bean of BCryptPasswordEncoder type.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
