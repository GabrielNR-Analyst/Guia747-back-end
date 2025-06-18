package com.guia747.places.dto;

import jakarta.validation.constraints.Size;

public record ContactData(
        @Size(max = 20) String phoneNumber,
        @Size(max = 200) String instagramUrl,
        @Size(max = 200) String facebookUrl,
        @Size(max = 200) String whatsappUrl
) {

}
