package com.guia747.cities.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ImageRequest(@NotBlank @URL String url) {

}
