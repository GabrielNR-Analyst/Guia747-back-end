package com.guia747.cities.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import com.guia747.common.PageResponse;

public class GetAllCitiesResponse extends PageResponse<CityResponse> {

    protected GetAllCitiesResponse(List<CityResponse> data, PaginationMetadata metadata) {
        super(data, metadata);
    }

    public static GetAllCitiesResponse from(Page<CityResponse> page) {
        return GetAllCitiesResponse.from(page, GetAllCitiesResponse::new);
    }
}
