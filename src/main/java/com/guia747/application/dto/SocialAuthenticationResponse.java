package com.guia747.application.dto;

import java.util.UUID;
import com.guia747.domain.vo.TokenPair;

public record SocialAuthenticationResponse(UUID userId, TokenPair tokenPair, boolean isNewAccount) {

}
