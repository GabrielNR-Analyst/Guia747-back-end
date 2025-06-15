package com.guia747.accounts.domain;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);
}
