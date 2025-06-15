package com.guia747.infrastructure.security;

import java.time.Instant;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.infrastructure.config.JwtProperties;

@Service
public class NimbusJwtTokenService implements JwtTokenService {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;

    public NimbusJwtTokenService(JwtProperties jwtProperties, JwtEncoder jwtEncoder) {
        this.jwtProperties = jwtProperties;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public JwtToken generateAccessToken(UserAccount userAccount) {
        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();

        Instant now = Instant.now();
        Instant expiration = now.plus(jwtProperties.getAccessTokenTtl());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userAccount.getId().toString())
                .issuedAt(now)
                .expiresAt(expiration)
                .build();

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(headers, claims);
        Jwt jwt = jwtEncoder.encode(encoderParameters);

        return new JwtToken(jwt.getTokenValue(), expiration);
    }
}
