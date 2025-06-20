package com.guia747.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "states")
@Getter
@Setter
public class JpaStateEntity extends JpaBaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "uf", nullable = false, unique = true, length = 2)
    private String uf;
}
