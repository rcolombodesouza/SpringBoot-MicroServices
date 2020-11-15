package com.store.security.token.converter;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import com.store.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

/**
 * Validate and decrypt token.
 *
 * @author rafaelcolombodesouza
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenConverter {

    private final JwtConfiguration jwtConfiguration;

    /**
     * Decrypt a given token.
     * @param encryptedToken token to be decrypted.
     * @return a signed token.
     */
    @SneakyThrows
    public String decryptToken(String encryptedToken) {
        log.info("Decrypting token.");
        JWEObject jweObject = JWEObject.parse(encryptedToken);
        DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfiguration.getPrivateKey().getBytes());
        jweObject.decrypt(directDecrypter);
        log.info("Token decrypted, returning signed token.");
        return jweObject.getPayload().toSignedJWT().serialize();
    }

    /**
     * Check if the token has a valid signature.
     * Executed in every request
     * @param signedToken token to be analyzed.
     */
    @SneakyThrows
    public void validateTokenSignature(String signedToken) {
        log.info("Validating token signature.");
        SignedJWT signedJWT = SignedJWT.parse(signedToken);
        log.info("Token parsed. Retrieving public key from signed token.");
        RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
        log.info("Public key retrieved. Validating signature.");
        if(!signedJWT.verify(new RSASSAVerifier(publicKey))) {
            throw new AccessDeniedException("Invalid token signature.");
        }
        log.info("Token has a valid signature.");
    }
}
