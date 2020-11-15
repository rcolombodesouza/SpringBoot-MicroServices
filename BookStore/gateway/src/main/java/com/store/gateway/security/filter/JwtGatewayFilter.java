package com.store.gateway.security.filter;

import com.nimbusds.jwt.SignedJWT;
import com.store.core.property.JwtConfiguration;
import com.store.security.filter.JwtTokenFilter;
import com.store.security.token.converter.TokenConverter;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static com.store.security.util.SecurityContextUtil.setSecurityContext;

/**
 * Filter class for the Gateway.
 *
 * @author rafaelcolombodesouza
 */
public class JwtGatewayFilter extends JwtTokenFilter {

    /**
     * Constructor.
     * @param jwtConfiguration JWT configuration.
     * @param tokenConverter Converter for the token.
     */
    public JwtGatewayFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }

    /**
     * This function is called in every request from each micro service.
     * @param httpServletRequest request.
     * @param httpServletResponse response.
     * @param filterChain filter.
     */
    @Override
    @SneakyThrows
    @SuppressWarnings("Duplicates")
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) {
        String header = httpServletRequest.getHeader(jwtConfiguration.getHeader().getName());
        if(header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();
        String signedToken = tokenConverter.decryptToken(token);
        tokenConverter.validateTokenSignature(signedToken);
        setSecurityContext(SignedJWT.parse(signedToken));

        // If token is only signed, it is going to replace the encrypted token in the 'Authorization' property
        // by a signed token.
        if(jwtConfiguration.getType().equalsIgnoreCase("signed")) {
            getCurrentContext().addZuulRequestHeader("Authorization",
                    jwtConfiguration.getHeader().getPrefix() + signedToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
