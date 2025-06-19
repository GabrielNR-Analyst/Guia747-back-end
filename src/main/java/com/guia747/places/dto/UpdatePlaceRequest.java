package com.guia747.places.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record UpdatePlaceRequest(
        String name,
        String about,
        @Valid AddressData address,
        @Valid ContactData contact,
        String youtubeVideoUrl,
        String thumbnailUrl,
        List<OperatingHoursData> operatingHours,
        List<FAQData> faqs
) {

    public record AddressData(
            @Size(max = 200) String street,
            @Size(max = 20) String number,
            @Size(max = 100) String complement,
            @Size(max = 100) String neighborhood,
            @Size(max = 20) String zipCode,
            Double latitude,
            Double longitude
    ) {

    }

    public record ContactData(
            @Size(max = 20) String phone,
            @Size(max = 100) String email,
            @Size(max = 20) String whatsappUrl,
            @Size(max = 100) String instagramUrl,
            @Size(max = 100) String facebookUrl
    ) {

    }
}
