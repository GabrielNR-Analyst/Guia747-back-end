package com.guia747.infrastructure.service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.vo.TokenPair;
import com.guia747.infrastructure.config.properties.AppProperties;
import com.guia747.infrastructure.config.properties.AppSecurityProperties.JwtProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JoseJwtTokenService implements JwtTokenService {

    private final JWSSigner signer;
    private final JWSVerifier verifier;
    private final JwtProperties jwtProperties;

    public JoseJwtTokenService(AppProperties appProperties) {
        this.jwtProperties = appProperties.security().jwt();

        try {
            // Ensure secret is at least 32 bytes for HS256
            byte[] secretBytes = ensureSecretLength(appProperties.security().jwt().secret());
            this.signer = new MACSigner(secretBytes);
            this.verifier = new MACVerifier(secretBytes);
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to initialize JWT signer/verifier", e);
        }
    }

    @Override
    public TokenPair generateTokenPair(UserAccount userAccount) {
        try {
            Instant now = Instant.now();
            Instant accessTokenExpiry = now.plus(jwtProperties.accessTokenExpiration());

            JWTClaimsSet accessTokenClaims = new JWTClaimsSet.Builder()
                    .subject(userAccount.getId().toString())
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(accessTokenExpiry))
                    .build();

            String accessToken = createSignedToken(accessTokenClaims);
            String refreshToken = "";

            return new TokenPair(
                    accessToken,
                    refreshToken,
                    jwtProperties.accessTokenExpiration().toSeconds(),
                    jwtProperties.refreshTokenExpiration().toSeconds()
            );
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(accessToken);

            // Verify signature
            if (!signedJWT.verify(verifier)) {
                return false;
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return !claims.getExpirationTime().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UUID extractAccountIdFromAccessToken(String accessToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(accessToken);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            return UUID.fromString(claims.getSubject());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String createSignedToken(JWTClaimsSet claims) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claims);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    private byte[] ensureSecretLength(String secret) {
        // HS256 requires at least 32 bytes (256 bits)
        byte[] secretBytes = secret.getBytes();

        if (secretBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must be at least 32 bytes long for HS256. Current length: " + secretBytes.length
            );
        }

        return secretBytes;
    }
}
