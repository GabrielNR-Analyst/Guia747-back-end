package com.guia747.authentication;

import java.util.Collections;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.accounts.UserAccount;
import com.guia747.accounts.UserAccountDetailsResponse;
import com.guia747.authentication.usecase.OAuth2AuthenticationUseCase;
import com.guia747.infrastructure.security.OAuth2AuthenticationToken;
import com.guia747.infrastructure.security.OAuth2UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/oauth2")
@Tag(name = "OAuth2 endpoints", description = "Base URL for OAuth2 authentication endpoints")
public class OAuth2AuthenticationController {

    private final OAuth2AuthenticationUseCase oauth2AuthenticationUseCase;
    private final SecurityContextRepository securityContextRepository;

    public OAuth2AuthenticationController(OAuth2AuthenticationUseCase oauth2AuthenticationUseCase,
            SecurityContextRepository securityContextRepository) {
        this.oauth2AuthenticationUseCase = oauth2AuthenticationUseCase;
        this.securityContextRepository = securityContextRepository;
    }

    @Operation(
            summary = "Authenticate with OAuth2 authorization code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(
                    schema = @Schema(implementation = UserAccountDetailsResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid input or missing parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content)
    })
    @PostMapping("/loginWithGoogle")
    public ResponseEntity<UserAccountDetailsResponse> loginWithGoogle(
            @Valid @RequestBody OAuth2AuthenticationRequest request,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        UserAccount userAccount = oauth2AuthenticationUseCase.execute(request);

        OAuth2UserPrincipal principal = new OAuth2UserPrincipal(userAccount);
        Authentication authentication = new OAuth2AuthenticationToken(principal, Collections.emptyList());
        authentication.setAuthenticated(true);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);

        return ResponseEntity.ok(UserAccountDetailsResponse.fromUserAccount(userAccount));
    }
}
