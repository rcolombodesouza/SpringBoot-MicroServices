package com.store.security.config;

import com.store.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Security config for the token micro service.
 *
 * @author rafaelcolombodesouza
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final JwtConfiguration jwtConfiguration;

    /**
     * Configure the http security.
     * @param http object to be configured.
     * @throws Exception in case it is not possible to configure the http object.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, exception) ->
                response.sendError(SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**",
                        "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
                .antMatchers("/book/v1/admin/**").hasRole("ADMIN")
                .antMatchers("/authentication/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();
    }
}
