package com.guia747.places.repository;

import java.util.List;
import com.guia747.places.entity.Category;

public interface CategoryRepository {

    Category save(Category category);

    boolean existsByName(String name);

    List<Category> findAll();
}
