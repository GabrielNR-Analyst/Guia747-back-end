package com.guia747.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.web.dtos.user.UserDetailsResponse;
import com.guia747.domain.users.entity.User;
import com.guia747.infrastructure.security.OAuth2UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "User Account", description = "User account management")
public class UserController {

    @Operation(summary = "Get current user profile", description = "Returns the profile of the authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponse> getCurrentUser(Authentication authentication) {
        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
        User user = principal.getUserAccount();
        return ResponseEntity.ok(UserDetailsResponse.from(user));
    }
}
