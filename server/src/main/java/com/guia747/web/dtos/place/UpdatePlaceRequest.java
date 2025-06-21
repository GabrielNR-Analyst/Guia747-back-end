package com.guia747.web.dtos.place;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePlaceRequest(
        String name,
        String about,
        @Valid AddressRequest address,
        @Valid ContactRequest contact,
        String youtubeVideoUrl,
        String thumbnailUrl,
        @Valid List<OperatingHoursRequest> operatingHours,
        @Valid List<FAQRequest> faqs,
        List<UUID> categoryIds
) {

    public record AddressRequest(
            @Size(min = 3, max = 255)
            @Pattern(regexp = "^[\\p{L}\\d\\s.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                    "pontos, apóstrofos e hífens")
            String street,

            @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Deve ser um CEP válido no formato XXXXX-XXX")
            String zipCode,

            @Pattern(regexp = "^[a-zA-Z0-9\\s-]*$", message = "O número do endereço contém caracteres inválidos.")
            String number,

            @Size(max = 100)
            @Size(max = 100) String complement,

            @Size(min = 3, max = 100)
            @Pattern(regexp = "^[\\p{L}\\d\\s.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                    "pontos, apóstrofos e hífens")
            @Size(max = 100) String neighborhood,

            BigDecimal latitude,

            BigDecimal longitude
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
