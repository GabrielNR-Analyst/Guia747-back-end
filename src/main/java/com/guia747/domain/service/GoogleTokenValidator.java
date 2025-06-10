package com.guia747.domain.service;

import com.guia747.domain.vo.GoogleUserInfo;

public interface GoogleTokenValidator {

    GoogleUserInfo validateTokenAndExtractUserInfo(String token);
}
