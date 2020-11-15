package com.store.security.token.creator;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.store.core.model.UserEntity;
import com.store.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import static com.nimbusds.jose.EncryptionMethod.A128CBC_HS256;
import static com.nimbusds.jose.JWEAlgorithm.DIR;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import static java.util.stream.Collectors.*;

/**
 * Class responsible for creating the token.
 *
 * @author rafaelcolombodesouza
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenCreator {

    private final JwtConfiguration jwtConfiguration;

    /**
     * Create a signed token.
     * @param auth authentication object from where the token will be signed.
     * @return the signed token.
     */
    @SneakyThrows
    public SignedJWT createSignedJWT(Authentication auth) {
        log.info("Starting to create the signed JWT (JWS).");
        UserEntity userEntity = (UserEntity) auth.getPrincipal();
        JWTClaimsSet jwtClaimsSet = createJWTClaimSet(auth, userEntity);
        KeyPair rsaKeys = generateKeyPair();
        log.info("Building JWK from RSA keys.");
        JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic()).keyID(UUID.randomUUID().toString()).build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(RS256)
                .jwk(jwk)
                .type(JOSEObjectType.JWT)
                .build(), jwtClaimsSet);
        log.info("Signing the token with the private RSA key.");
        RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());
        signedJWT.sign(signer);
        log.info(String.format("Serialized token %s.", signedJWT.serialize()));
        return signedJWT;
    }

    /**
     * Create a JSON Web Token Claim Set.
     * @param auth authentication object.
     * @param userEntity the user entity.
     * @return a JSON Web Token Claim Set.
     */
    private JWTClaimsSet createJWTClaimSet(Authentication auth, UserEntity userEntity) {
        log.info(String.format("Creating the JWTClaimSet object for %s.", userEntity.toString()));
        return new JWTClaimsSet.Builder().subject(userEntity.getUsername())
                .claim("authorities", auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .claim("userId", userEntity.getId())
                .issuer("http://com.store")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000)))
                .build();
    }

    /**
     * Generate the Key Pair in the RSA 2048 format.
     * @return the KeyPair object.
     */
    @SneakyThrows
    private KeyPair generateKeyPair() {
        log.info("Generating RSA 2048 bits keys.");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.genKeyPair();
    }

    /**
     * Encrypt the given token.
     * @param signedJWT token to be encrypted.
     * @return the encrypted token.
     * @throws JOSEException in case it is not possible to encrypt the token.
     */
    public String encryptToken(SignedJWT signedJWT) throws JOSEException {
        log.info("Starting to encrypt token method.");
        DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfiguration.getPrivateKey().getBytes());
        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(DIR, A128CBC_HS256)
                .contentType("JWT")
                .build(), new Payload(signedJWT));
        log.info("Encrypting token with system's private key.");
        jweObject.encrypt(directEncrypter);
        log.info("Token encrypted.");
        return jweObject.serialize();
    }
}
