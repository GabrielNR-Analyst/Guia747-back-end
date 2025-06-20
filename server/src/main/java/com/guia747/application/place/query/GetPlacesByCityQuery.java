package com.guia747.application.place.query;

import java.util.UUID;
import org.springframework.data.domain.PageRequest;

public record GetPlacesByCityQuery(UUID cityId, PageRequest pageable) {

}
