package com.guia747.web.dtos.city;

import java.util.List;
import org.springframework.data.domain.Page;
import com.guia747.shared.PageResponse;

public class GetAllCitiesResponse extends PageResponse<CityDetailsResponse> {

    protected GetAllCitiesResponse(List<CityDetailsResponse> data, PaginationMetadata metadata) {
        super(data, metadata);
    }

    public static GetAllCitiesResponse from(Page<CityDetailsResponse> page) {
        return GetAllCitiesResponse.from(page, GetAllCitiesResponse::new);
    }
}
