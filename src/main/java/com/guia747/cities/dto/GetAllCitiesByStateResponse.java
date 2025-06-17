package com.guia747.cities.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import com.guia747.common.PageResponse;

public class GetAllCitiesByStateResponse extends PageResponse<CityResponse> {

    protected GetAllCitiesByStateResponse(List<CityResponse> data, PaginationMetadata metadata) {
        super(data, metadata);
    }

    public static GetAllCitiesByStateResponse from(Page<CityResponse> page) {
        return GetAllCitiesByStateResponse.from(page, GetAllCitiesByStateResponse::new);
    }
}
