package com.guia747.web.dtos.place;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePlaceRequest(
        String name,
        String about,
        @Valid UpdatePlaceRequest.AddressRequest address,
        @Valid UpdatePlaceRequest.ContactRequest contact,
        String youtubeVideoUrl,
        String thumbnailUrl,
        @Valid List<OperatingHoursRequest> operatingHours,
        @Valid List<FAQRequest> faqs
) {

    public record AddressRequest(
            @Size(max = 200) String street,
            @Size(max = 20) String number,
            @Size(max = 100) String complement,
            @Size(max = 100) String neighborhood,
            @Size(max = 20) String zipCode,
            Double latitude,
            Double longitude
    ) {

    }

    public record ContactRequest(
            @Size(max = 20) String phone,
            @Size(max = 100) String email,
            @Size(max = 20) String whatsappUrl,
            @Size(max = 100) String instagramUrl,
            @Size(max = 100) String facebookUrl
    ) {

    }

    public record FAQRequest(
            @NotBlank String question,
            @NotBlank String answer
    ) {

    }

}
