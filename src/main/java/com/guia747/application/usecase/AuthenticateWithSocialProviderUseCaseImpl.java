package com.guia747.application.usecase;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.dto.SocialAuthenticationResult;
import com.guia747.domain.entity.RefreshTokenSession;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.exception.SocialProviderAlreadyLinkedException;
import com.guia747.domain.repository.RefreshTokenSessionRepository;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.domain.service.SocialAuthenticationProvider;
import com.guia747.domain.vo.SocialAuthenticationToken;
import com.guia747.domain.vo.SocialUserProfile;
import com.guia747.domain.vo.TokenPair;
import com.guia747.infrastructure.service.JwtTokenService;

@Service
public class AuthenticateWithSocialProviderUseCaseImpl implements AuthenticateWithSocialProviderUseCase {

    private final SocialAuthenticationProvider socialAuthenticationProvider;
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenSessionRepository refreshTokenRepository;

    public AuthenticateWithSocialProviderUseCaseImpl(
            SocialAuthenticationProvider socialAuthenticationProvider,
            UserAccountRepository userAccountRepository,
            JwtTokenService jwtTokenService,
            RefreshTokenSessionRepository refreshTokenRepository
    ) {
        this.socialAuthenticationProvider = socialAuthenticationProvider;
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public SocialAuthenticationResult execute(String authenticationToken) {
        var authToken = new SocialAuthenticationToken(authenticationToken);
        SocialUserProfile socialUserProfile = socialAuthenticationProvider.validateTokenAndExtractProfile(authToken);

        AuthenticationResult authResult = findOrCreateUserAccount(socialUserProfile);
        UserAccount userAccount = authResult.userAccount();

        TokenPair tokenPair = jwtTokenService.generateTokenPair(userAccount.getId());
        RefreshTokenSession session = new RefreshTokenSession(
                tokenPair.refreshToken(),
                userAccount.getId(),
                tokenPair.refreshTokenTtl()
        );
        refreshTokenRepository.save(session);

        return new SocialAuthenticationResult(userAccount.getId(), tokenPair, authResult.isNewAccount());
    }

    private AuthenticationResult findOrCreateUserAccount(SocialUserProfile socialProfile) {
        // Try to find by social provider ID (exact match)
        Optional<UserAccount> existingBySocial = userAccountRepository.findByGoogleId(socialProfile.providerId());
        if (existingBySocial.isPresent()) {
            return new AuthenticationResult(existingBySocial.get(), false);
        }

        // Try to find by email (reconnection scenario)
        Optional<UserAccount> existingByEmail = userAccountRepository.findByEmail(socialProfile.email());
        if (existingByEmail.isPresent()) {
            UserAccount existing = existingByEmail.get();

            // Check if this provider is already linked to a DIFFERENT account
            var accountWithSameProvider = userAccountRepository.findByGoogleId(socialProfile.providerId());
            if (accountWithSameProvider.isPresent()
                    && !accountWithSameProvider.get().getGoogleId().equals(existing.getGoogleId())) {
                throw new SocialProviderAlreadyLinkedException("google");
            }

            // Update the existing account with new provider
            existing.reconnectSocialProfile(socialProfile);
            userAccountRepository.save(existing);

            return new AuthenticationResult(existing, false);
        }

        // NEW USER: Create completely new account
        UserAccount newAccount = UserAccount.createFromSocialProfile(socialProfile);
        UserAccount savedAccount = userAccountRepository.save(newAccount);

        return new AuthenticationResult(savedAccount, true);
    }

    record AuthenticationResult(UserAccount userAccount, boolean isNewAccount) {

    }
}
