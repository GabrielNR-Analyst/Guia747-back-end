package com.guia747.authentication.domain;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenSession implements Serializable {

    private UUID accountId;
    private String tokenValue;
    private String ipAddress;
    private String userAgent;
}
