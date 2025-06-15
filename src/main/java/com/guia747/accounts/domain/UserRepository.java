package com.guia747.accounts.domain;

import java.util.Optional;

public interface UserRepository {

    UserAccount save(UserAccount userAccount);

    Optional<UserAccount> findBySocialProvider(String providerName, String providerId);
}
