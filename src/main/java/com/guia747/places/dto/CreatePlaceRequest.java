package com.guia747.places.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePlaceRequest(
        @NotNull UUID cityId,
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String about,
        @NotNull @Valid AddressData address,
        @Valid ContactData contact,
        // ---
        List<OperatingHoursData> operatingHours,
        List<FAQData> faqs,
        Set<UUID> categoryIds,
        @Size(max = 200) String youtubeVideoUrl,
        @Size(max = 200) String thumbnailUrl
) {

}
