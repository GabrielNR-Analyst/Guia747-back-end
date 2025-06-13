package com.guia747.authentication;

import org.springframework.stereotype.Service;
import com.guia747.infrastructure.oauth2.OAuth2SecurityContext;
import com.guia747.infrastructure.oauth2.OAuth2SecurityContextCodec;
import com.guia747.infrastructure.oauth2.OAuth2SecurityContextException;
import com.guia747.infrastructure.oauth2.OAuth2TokenExchangeResponse;
import com.guia747.infrastructure.oauth2.OAuth2TokenExchangeService;

@Service
public class DefaultCompleteOAuth2AuthorizationUseCase implements CompleteOAuth2AuthorizationUseCase {

    private final OAuth2SecurityContextCodec securityContextCodec;
    private final OAuth2TokenExchangeService oauth2TokenExchangeService;

    public DefaultCompleteOAuth2AuthorizationUseCase(OAuth2SecurityContextCodec securityContextCodec,
            OAuth2TokenExchangeService oauth2TokenExchangeService) {
        this.securityContextCodec = securityContextCodec;
        this.oauth2TokenExchangeService = oauth2TokenExchangeService;
    }

    @Override
    public void execute(CompleteOAuth2AuthorizationCommand command) {
        OAuth2SecurityContext securityContext = securityContextCodec.decodeContext(
                command.securityContextCookieValue());

        if (!command.providerName().equals(securityContext.providerName())) {
            throw new OAuth2SecurityContextException("Provider mismatch in security context");
        }

        if (!command.state().equals(securityContext.state())) {
            throw new OAuth2SecurityContextException("State mismatch in security context");
        }

        OAuth2TokenExchangeResponse tokenResponse = oauth2TokenExchangeService.exchangeAuthorizationCode(
                command.providerName(), command.authorizationCode(), command.state(), securityContext.pkceVerifier());

        OAuth2UserInfo oauth2UserInfo = oauth2TokenExchangeService.fetchUserInfo(command.providerName(),
                tokenResponse.getAccessToken());
    }
}
