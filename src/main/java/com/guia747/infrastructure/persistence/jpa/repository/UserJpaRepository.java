package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.UserAccountJpaEntity;

public interface UserJpaRepository extends JpaRepository<UserAccountJpaEntity, UUID> {

}
