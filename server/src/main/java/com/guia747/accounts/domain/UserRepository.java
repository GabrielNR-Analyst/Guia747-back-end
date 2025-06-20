package com.guia747.accounts.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UserAccount save(UserAccount userAccount);

    Optional<UserAccount> findById(UUID accountId);

    Optional<UserAccount> findBySocialProvider(String providerName, String providerId);
}
