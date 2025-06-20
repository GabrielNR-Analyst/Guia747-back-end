package com.guia747.application.city.query;

import org.springframework.data.domain.Pageable;

public record GetAllCitiesByUfQuery(String uf, Pageable pageable) {

}
