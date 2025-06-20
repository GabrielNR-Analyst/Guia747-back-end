package com.guia747.web.dtos.place;

import java.util.List;
import org.springframework.data.domain.Page;
import com.guia747.shared.PageResponse;

public class GetAllPlacesResponse extends PageResponse<PlaceDetailsResponse> {

    public GetAllPlacesResponse(List<PlaceDetailsResponse> data, PaginationMetadata metadata) {
        super(data, metadata);
    }

    public static GetAllPlacesResponse from(Page<PlaceDetailsResponse> place) {
        return GetAllPlacesResponse.from(place, GetAllPlacesResponse::new);
    }
}
