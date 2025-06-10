package com.guia747.application.usecase;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.dto.SocialAuthenticationResult;
import com.guia747.domain.entity.UserAccount;
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

    public AuthenticateWithSocialProviderUseCaseImpl(
            SocialAuthenticationProvider socialAuthenticationProvider,
            UserAccountRepository userAccountRepository,
            JwtTokenService jwtTokenService) {
        this.socialAuthenticationProvider = socialAuthenticationProvider;
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    @Transactional
    public SocialAuthenticationResult execute(String authenticationToken) {
        var authToken = new SocialAuthenticationToken(authenticationToken);
        SocialUserProfile socialUserProfile = socialAuthenticationProvider.validateTokenAndExtractProfile(authToken);

        Optional<UserAccount> existingAccount = userAccountRepository.findByGoogleId(socialUserProfile.providerId());
        UserAccount userAccount = existingAccount.orElseGet(() ->
                userAccountRepository.save(UserAccount.createFromSocialProfile(socialUserProfile))
        );
        boolean isNewAccount = existingAccount.isEmpty();

        TokenPair tokenPair = jwtTokenService.generateTokenPair(userAccount.getId());
        return new SocialAuthenticationResult(userAccount.getId(), tokenPair, isNewAccount);
    }
}
