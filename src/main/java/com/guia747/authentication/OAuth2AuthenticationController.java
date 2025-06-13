package com.guia747.authentication;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.infrastructure.oauth2.OAuth2AuthorizationResult;

@RestController
@RequestMapping("/api/login/oauth2/")
public class OAuth2AuthenticationController {

    private final InitiateOAuth2AuthorizationUseCase initiateOAuth2AuthorizationUseCase;

    public OAuth2AuthenticationController(InitiateOAuth2AuthorizationUseCase initiateOAuth2AuthorizationUseCase) {
        this.initiateOAuth2AuthorizationUseCase = initiateOAuth2AuthorizationUseCase;
    }

    @GetMapping("/{providerName}/authorize")
    public ResponseEntity<Void> authorize(@PathVariable String providerName) {
        OAuth2AuthorizationResult result = initiateOAuth2AuthorizationUseCase.execute(providerName);
        URI locationUri = URI.create(result.authorizationUrl());
        return ResponseEntity.status(HttpStatus.FOUND).location(locationUri).build();
    }
}
