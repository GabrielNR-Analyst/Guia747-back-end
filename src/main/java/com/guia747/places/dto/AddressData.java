package com.guia747.places.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressData(
        @NotBlank @Size(max = 200) String street,
        @Size(max = 20) String zipCode,
        @Size(max = 20) String number,
        @NotBlank @Size(max = 100) String neighborhood,
        @Size(max = 100) String complement
) {

}
