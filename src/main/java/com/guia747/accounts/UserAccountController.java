package com.guia747.accounts;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.infrastructure.security.OAuth2UserPrincipal;

@RestController
@RequestMapping("/api/v1/accounts")
public class UserAccountController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
        UserAccount userAccount = principal.getUserAccount();
        return ResponseEntity.status(401).body("Not authenticated");
    }
}
