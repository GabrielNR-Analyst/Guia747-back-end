package com.guia747.application.place.usecase;

import com.guia747.web.dtos.place.CategoryResponse;
import com.guia747.web.dtos.place.CreatePlaceCategoryRequest;

public interface CreatePlaceCategoryUseCase {

    CategoryResponse execute(CreatePlaceCategoryRequest request);
}
