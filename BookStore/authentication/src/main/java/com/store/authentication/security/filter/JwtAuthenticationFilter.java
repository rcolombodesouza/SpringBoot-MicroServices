package com.store.authentication.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import com.store.core.model.UserEntity;
import com.store.core.property.JwtConfiguration;
import com.store.security.token.creator.TokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * Username and password filter.
 *
 * @author rafaelcolombodesouza
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final TokenCreator tokenCreator;

    /**
     * Method to handle the attemp to authenticate in the system.
     * @param request http request.
     * @param response http response.
     * @return an Authentication object.
     */
    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("Attempting authentication.");
        UserEntity userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
        if(userEntity == null) {
            throw new UsernameNotFoundException("Unable to retrieve the username or password.");
        }
        log.info(String.format("Creating the authentication object for the user %s and calling " +
                "UserDetailsServiceImpl loadUserByUserName.", userEntity.toString()));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(),
                        Collections.emptyList());
        usernamePasswordAuthenticationToken.setDetails(userEntity);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    /**
     * In case of success in teh authentication, this method will be called.
     * @param request http request.
     * @param response http response.
     * @param chain the filter.
     * @param authResult result of the authentication.
     */
    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        log.info(String.format("Authentication was successful for the user %s, generating JWE token.", authResult.getName()));
        SignedJWT signedJWT = tokenCreator.createSignedJWT(authResult);
        String encryptedToken = tokenCreator.encryptToken(signedJWT);
        log.info("Token generated successfully, adding it to the response header.");
        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfiguration.getHeader().getName());
        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + encryptedToken);
    }
}