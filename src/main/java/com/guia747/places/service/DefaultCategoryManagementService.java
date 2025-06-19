package com.guia747.places.service;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.infrastructure.util.SlugGenerator;
import com.guia747.places.dto.CreateCategoryRequest;
import com.guia747.places.entity.Category;
import com.guia747.places.exception.CategoryAlreadyExistsException;
import com.guia747.places.repository.CategoryRepository;

@Service
public class DefaultCategoryManagementService implements CategoryManagementService {

    private final CategoryRepository categoryRepository;

    public DefaultCategoryManagementService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException(request.name());
        }

        String slug = SlugGenerator.generateSlug(request.name());
        Category category = Category.createNew(request.name(), slug, request.description());

        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allCategories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
