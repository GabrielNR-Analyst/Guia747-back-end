package com.guia747.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.dto.SocialAuthenticationResult;
import com.guia747.application.usecase.AuthenticateWithSocialProviderUseCase;
import com.guia747.web.dto.AuthenticationResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticateWithSocialProviderUseCase authenticateWithSocialProviderUseCase;

    public AuthenticationController(AuthenticateWithSocialProviderUseCase authenticateWithSocialProviderUseCase) {
        this.authenticateWithSocialProviderUseCase = authenticateWithSocialProviderUseCase;
    }

    @PostMapping("/google/authenticate")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            HttpServletResponse httpResponse
    ) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        SocialAuthenticationResult result = authenticateWithSocialProviderUseCase.execute(accessToken);

        var response = new AuthenticationResponse(
                result.userId(),
                result.tokenPair().accessToken(),
                result.tokenPair().accessTokenExpirationInSeconds(),
                "Bearer"
        );

        HttpStatus status = result.isNewAccount() ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }
}
