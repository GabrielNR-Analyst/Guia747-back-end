package com.guia747.application.usecase;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.domain.service.GoogleTokenValidator;
import com.guia747.domain.vo.GoogleUserInfo;
import com.guia747.web.dto.GoogleAuthenticationResponse;

@Service
public class AuthenticateWithGoogleUseCaseImpl implements AuthenticateWithGoogleUseCase {

    private final GoogleTokenValidator googleTokenValidator;
    private final UserAccountRepository userAccountRepository;

    public AuthenticateWithGoogleUseCaseImpl(
            GoogleTokenValidator googleTokenValidator,
            UserAccountRepository userAccountRepository
    ) {
        this.googleTokenValidator = googleTokenValidator;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public GoogleAuthenticationResponse execute(String googleIdToken) {
        GoogleUserInfo googleUserInfo = googleTokenValidator.validateTokenAndExtractUserInfo(googleIdToken);

        Optional<UserAccount> existingAccount = userAccountRepository.findByGoogleId(googleUserInfo.googleId());
        UserAccount userAccount = existingAccount.orElseGet(() ->
                userAccountRepository.save(UserAccount.createFromGoogleAuth(googleUserInfo))
        );

        boolean isNewUser = existingAccount.isEmpty();

        return new GoogleAuthenticationResponse(userAccount.getId(), isNewUser);
    }
}
