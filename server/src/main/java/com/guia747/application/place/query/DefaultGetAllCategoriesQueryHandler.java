package com.guia747.application.place.query;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.places.repository.CategoryRepository;
import com.guia747.web.dtos.place.CategoryResponse;

@Service
public class DefaultGetAllCategoriesQueryHandler implements GetAllCategoriesQueryHandler {

    private final CategoryRepository categoryRepository;

    public DefaultGetAllCategoriesQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allCategories")
    public List<CategoryResponse> handle() {
        return categoryRepository.findAll().stream().map(CategoryResponse::from).toList();
    }
}
