package com.store.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.store.core.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * This class will manage the security context
 *
 * @author rafaelcolombodesouza
 */
@Slf4j
public class SecurityContextUtil {

    private SecurityContextUtil() {

    }

    /**
     * Build a security context based on the signed token.
     * @param signedJWT token.
     */
    public static void setSecurityContext(SignedJWT signedJWT) {
        try {
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            String username = jwtClaimsSet.getSubject();
            if(username == null) {
                throw new JOSEException("Username missing from JWT.");
            }
            List<String> authorities = jwtClaimsSet.getStringListClaim("authorities");
            UserEntity userEntity = UserEntity
                    .builder()
                    .id(jwtClaimsSet.getLongClaim("userId"))
                    .username(username)
                    .role(String.join(",", authorities))
                    .build();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userEntity, null, createAuthorities(authorities));
            authentication.setDetails(signedJWT.serialize());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("Error setting security context.", e);
            SecurityContextHolder.clearContext();
        }
    }

    private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
}
