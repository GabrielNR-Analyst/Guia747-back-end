package com.guia747.domain.repository;

import java.util.Optional;
import java.util.UUID;
import com.guia747.domain.entity.UserAccount;

public interface UserAccountRepository {

    UserAccount save(UserAccount userAccount);

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByGoogleId(String googleId);

    Optional<UserAccount> findById(UUID id);
}
