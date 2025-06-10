package com.guia747.application.usecase;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.dto.GoogleAuthenticationResult;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.domain.service.GoogleTokenValidator;
import com.guia747.domain.vo.GoogleUserInfo;
import com.guia747.domain.vo.TokenPair;
import com.guia747.infrastructure.service.JwtTokenService;

@Service
public class AuthenticateWithGoogleUseCaseImpl implements AuthenticateWithGoogleUseCase {

    private final GoogleTokenValidator googleTokenValidator;
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenService jwtTokenService;

    public AuthenticateWithGoogleUseCaseImpl(
            GoogleTokenValidator googleTokenValidator,
            UserAccountRepository userAccountRepository,
            JwtTokenService jwtTokenService) {
        this.googleTokenValidator = googleTokenValidator;
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    @Transactional
    public GoogleAuthenticationResult execute(String googleIdToken) {
        GoogleUserInfo googleUserInfo = googleTokenValidator.validateTokenAndExtractUserInfo(googleIdToken);

        Optional<UserAccount> existingAccount = userAccountRepository.findByGoogleId(googleUserInfo.googleId());
        UserAccount userAccount = existingAccount.orElseGet(() ->
                userAccountRepository.save(UserAccount.createFromGoogleAuth(googleUserInfo))
        );
        boolean isNewUser = existingAccount.isEmpty();

        TokenPair tokenPair = jwtTokenService.generateTokenPair(userAccount);
        return new GoogleAuthenticationResult(userAccount.getId(), isNewUser, tokenPair);
    }
}
