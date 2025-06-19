package com.guia747.domain.places.repository;

import java.util.List;
import java.util.UUID;
import com.guia747.domain.places.entity.Category;

public interface CategoryRepository {

    Category save(Category category);

    boolean existsByName(String name);

    List<Category> findAll();

    List<Category> findAllByIdIn(List<UUID> ids);
}
