package com.guia747.web.dtos.place;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreatePlaceRequest(
        @NotNull UUID cityId,
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String about,
        @NotNull @Valid AddressRequest address,
        @Valid ContactData contact,
        @Valid List<OperatingHoursRequest> operatingHours,
        List<UUID> categoryIds
) {

    public record ContactData(
            @Size(max = 20) String phoneNumber,
            @Size(max = 200) @URL String instagramUrl,
            @Size(max = 200) @URL String facebookUrl,
            @Size(max = 200) @URL String whatsappUrl,
            @Size(max = 255) @Email String email
    ) {

    }

}
