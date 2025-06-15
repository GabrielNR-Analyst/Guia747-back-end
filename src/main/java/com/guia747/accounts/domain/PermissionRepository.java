package com.guia747.accounts.domain;

import java.util.Optional;

public interface PermissionRepository {

    Optional<Permission> findByName(String name);
}
