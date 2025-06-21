package com.guia747.application.place.usecase.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.place.usecase.CreatePlaceCategoryUseCase;
import com.guia747.domain.places.entity.Category;
import com.guia747.domain.places.exception.CategoryAlreadyExistsException;
import com.guia747.domain.places.repository.CategoryRepository;
import com.guia747.infrastructure.util.SlugGenerator;
import com.guia747.web.dtos.place.CategoryResponse;
import com.guia747.web.dtos.place.CreatePlaceCategoryRequest;

@Service
public class DefaultCreatePlaceCategoryUseCase implements CreatePlaceCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public DefaultCreatePlaceCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse execute(CreatePlaceCategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException(request.name());
        }

        String slug = SlugGenerator.generateSlug(request.name(), categoryRepository::existsBySlug);
        Category category = Category.createNew(request.name(), slug, request.description());

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.from(savedCategory);
    }
}
