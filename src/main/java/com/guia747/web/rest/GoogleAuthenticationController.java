package com.guia747.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.usecase.AuthenticateWithGoogleUseCase;
import com.guia747.web.dto.GoogleAuthenticationResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class GoogleAuthenticationController {

    private final AuthenticateWithGoogleUseCase authenticateWithGoogleUseCase;

    public GoogleAuthenticationController(AuthenticateWithGoogleUseCase authenticateWithGoogleUseCase) {
        this.authenticateWithGoogleUseCase = authenticateWithGoogleUseCase;
    }

    @PostMapping("/login-with-google")
    public ResponseEntity<GoogleAuthenticationResponse> loginWithGoogle(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        return ResponseEntity.ok(authenticateWithGoogleUseCase.execute(accessToken));
    }
}
