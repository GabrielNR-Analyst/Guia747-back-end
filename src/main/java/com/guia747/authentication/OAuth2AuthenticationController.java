package com.guia747.authentication;

import java.net.URI;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.infrastructure.oauth2.OAuth2AuthorizationCookieService;
import com.guia747.infrastructure.oauth2.OAuth2AuthorizationResult;

@RestController
@RequestMapping("/api/login/oauth2/")
public class OAuth2AuthenticationController {

    private final InitiateOAuth2AuthorizationUseCase initiateOAuth2AuthorizationUseCase;
    private final OAuth2AuthorizationCookieService authorizationCookieService;
    private final CompleteOAuth2AuthorizationUseCase completeOAuth2AuthorizationUseCase;

    public OAuth2AuthenticationController(
            InitiateOAuth2AuthorizationUseCase initiateOAuth2AuthorizationUseCase,
            OAuth2AuthorizationCookieService authorizationCookieService,
            CompleteOAuth2AuthorizationUseCase completeOAuth2AuthorizationUseCase) {
        this.initiateOAuth2AuthorizationUseCase = initiateOAuth2AuthorizationUseCase;
        this.authorizationCookieService = authorizationCookieService;
        this.completeOAuth2AuthorizationUseCase = completeOAuth2AuthorizationUseCase;
    }

    @GetMapping("/{providerName}/authorize")
    public ResponseEntity<Void> authorize(@PathVariable String providerName, HttpServletResponse httpResponse) {
        OAuth2AuthorizationResult result = initiateOAuth2AuthorizationUseCase.execute(providerName);
        authorizationCookieService.setAuthorizationCookie(httpResponse, result.securityContext());

        URI locationUri = URI.create(result.authorizationUrl());
        return ResponseEntity.status(HttpStatus.FOUND).location(locationUri).build();
    }

    @GetMapping("/{providerName}/callback")
    public ResponseEntity<Void> callback(@PathVariable String providerName, @RequestParam String code,
            @RequestParam String state, @CookieValue("oauth2_sec") String securityContextCookieValue) {
        CompleteOAuth2AuthorizationCommand command = new CompleteOAuth2AuthorizationCommand(providerName, code, state,
                securityContextCookieValue);
        completeOAuth2AuthorizationUseCase.execute(command);
        
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}
