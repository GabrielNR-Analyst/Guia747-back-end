package com.guia747.application.dto;

import java.util.UUID;
import com.guia747.domain.vo.TokenPair;

public record GoogleAuthenticationResult(UUID userId, boolean isNewUser, TokenPair tokenPair) {

}
