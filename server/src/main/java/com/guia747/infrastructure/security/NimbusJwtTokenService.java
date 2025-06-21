package com.guia747.infrastructure.security;

import java.time.Instant;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import com.guia747.domain.users.entity.User;
import com.guia747.infrastructure.config.JwtProperties;

@Service
public class NimbusJwtTokenService implements JwtTokenService {

    private static final int REFRESH_TOKEN_BYTES_LENGTH = 64;

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;
    private final SecureRandomBase64TokenGenerator secureRandomBase64TokenGenerator;

    public NimbusJwtTokenService(JwtProperties jwtProperties, JwtEncoder jwtEncoder,
            SecureRandomBase64TokenGenerator secureRandomBase64TokenGenerator) {
        this.jwtProperties = jwtProperties;
        this.jwtEncoder = jwtEncoder;
        this.secureRandomBase64TokenGenerator = secureRandomBase64TokenGenerator;
    }

    @Override
    public JwtTokenPair generateTokenPair(User user) {
        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();

        Instant now = Instant.now();
        Instant expiration = now.plus(jwtProperties.getAccessTokenTtl());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(expiration)
                .build();

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(headers, claims);
        Jwt jwt = jwtEncoder.encode(encoderParameters);

        // Generate refresh token
        String refreshToken = secureRandomBase64TokenGenerator.generate(REFRESH_TOKEN_BYTES_LENGTH);

        return new JwtTokenPair(jwt.getTokenValue(), refreshToken, jwtProperties.getAccessTokenTtl(),
                jwtProperties.getRefreshTokenTtl());
    }
}
