package com.guia747.domain.users.repository;

import java.util.Optional;
import java.util.UUID;
import com.guia747.domain.users.entity.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID accountId);

    Optional<User> findBySocialProvider(String providerName, String providerId);
}
