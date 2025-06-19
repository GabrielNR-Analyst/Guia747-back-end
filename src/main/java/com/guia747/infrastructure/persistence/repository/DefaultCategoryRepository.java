package com.guia747.infrastructure.persistence.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCategoryEntity;
import com.guia747.infrastructure.persistence.jpa.repository.CategoryJpaRepository;
import com.guia747.infrastructure.persistence.mapper.CategoryJpaMapper;
import com.guia747.domain.places.entity.Category;
import com.guia747.domain.places.repository.CategoryRepository;

@Repository
public class DefaultCategoryRepository implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryJpaMapper mapper;

    public DefaultCategoryRepository(CategoryJpaRepository jpaRepository, CategoryJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Category save(Category category) {
        JpaCategoryEntity entity = mapper.toEntity(category);
        JpaCategoryEntity savedCategory = jpaRepository.save(entity);
        return mapper.toDomain(savedCategory);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByNameEqualsIgnoreCase(name);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
