package com.guia747.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.dto.GoogleAuthenticationResult;
import com.guia747.application.usecase.AuthenticateWithGoogleUseCase;
import com.guia747.web.dto.AuthenticationResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class GoogleAuthenticationController {

    private final AuthenticateWithGoogleUseCase authenticateWithGoogleUseCase;

    public GoogleAuthenticationController(AuthenticateWithGoogleUseCase authenticateWithGoogleUseCase) {
        this.authenticateWithGoogleUseCase = authenticateWithGoogleUseCase;
    }

    @PostMapping("/login-with-google")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            HttpServletResponse httpResponse
    ) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        GoogleAuthenticationResult result = authenticateWithGoogleUseCase.execute(accessToken);

        var response = new AuthenticationResponse(
                result.userId(),
                result.tokenPair().accessToken(),
                "Bearer",
                result.tokenPair().accessTokenExpirationInSeconds(),
                result.isNewUser()
        );

        return ResponseEntity.ok(response);
    }
}
