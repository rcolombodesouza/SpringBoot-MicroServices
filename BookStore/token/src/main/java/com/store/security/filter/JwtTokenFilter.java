package com.store.security.filter;

import com.nimbusds.jwt.SignedJWT;
import com.store.core.property.JwtConfiguration;
import com.store.security.token.converter.TokenConverter;
import com.store.security.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class will be executed in every request.
 *
 * @author rafaelcolombodesouza
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenFilter extends OncePerRequestFilter {

    protected final JwtConfiguration jwtConfiguration;
    protected final TokenConverter tokenConverter;

    /**
     * This function is called in every request from each micro service.
     * @param httpServletRequest request.
     * @param httpServletResponse response.
     * @param filterChain filter.
     * @throws ServletException if it fails to apply the filter.
     * @throws IOException if it fails to apply the filter.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get the property 'Authorization' from the header from the request.
        String header = httpServletRequest.getHeader(jwtConfiguration.getHeader().getName());

        // In case of a login, it is not necessary to authenticate.
        if(header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Remove the prefix from the token.
        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

        // Validate the token if the type is signed, otherwise decrypt first and validate later.
        SecurityContextUtil.setSecurityContext(StringUtils.equalsIgnoreCase("signed",
                jwtConfiguration.getType()) ? validateToken(token) : decryptAndValidateToken(token));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Decrypt and validate the given token.
     * @param encryptedToken token to be analyzed.
     * @return the signed token.
     */
    @SneakyThrows
    private SignedJWT decryptAndValidateToken(String encryptedToken) {
        String signedToken = tokenConverter.decryptToken(encryptedToken);
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }

    /**
     * Validate the given token.
     * @param signedToken token to be analyzed.
     * @return the signed token.
     */
    @SneakyThrows
    private SignedJWT validateToken(String signedToken) {
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }
}
