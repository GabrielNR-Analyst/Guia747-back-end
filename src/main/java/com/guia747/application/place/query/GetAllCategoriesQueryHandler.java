package com.guia747.application.place.query;

import java.util.List;
import com.guia747.web.dtos.place.CategoryResponse;

public interface GetAllCategoriesQueryHandler {

    List<CategoryResponse> handle();
}
