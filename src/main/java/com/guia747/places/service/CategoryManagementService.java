package com.guia747.places.service;

import java.util.List;
import com.guia747.places.dto.CreateCategoryRequest;
import com.guia747.places.entity.Category;

public interface CategoryManagementService {

    Category createCategory(CreateCategoryRequest request);

    List<Category> getAllCategories();
}
