package com.guia747.authentication.domain;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenSession implements Serializable {

    private UUID accountId;
    private String tokenValue;
    private String ipAddress;
    private String userAgent;
}
