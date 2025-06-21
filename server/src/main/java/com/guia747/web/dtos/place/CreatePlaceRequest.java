package com.guia747.web.dtos.place;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreatePlaceRequest(
        @NotNull UUID cityId,
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String about,
        @NotNull @Valid CreatePlaceRequest.AddressRequest address,
        @Valid CreatePlaceRequest.ContactRequest contact,
        @Valid List<OperatingHoursRequest> operatingHours,
        List<UUID> categoryIds
) {

    public record ContactRequest(
            @Size(max = 20) String phoneNumber,
            @Size(max = 200) @URL String instagramUrl,
            @Size(max = 200) @URL String facebookUrl,
            @Size(max = 200) @URL String whatsappUrl,
            @Size(max = 255) @Email String email
    ) {

    }

    public record AddressRequest(
            @NotBlank
            @Size(min = 3, max = 255)
            @Pattern(regexp = "^[\\p{L}\\d\\s.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                    "pontos, apóstrofos e hífens")
            String street,

            @NotBlank
            @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Deve ser um CEP válido no formato XXXXX-XXX")
            String zipCode,

            @Size(max = 20)
            @Pattern(regexp = "^[a-zA-Z0-9\\s-]*$", message = "O número do endereço contém caracteres inválidos.")
            String number,

            @NotBlank @Size(min = 3, max = 100)
            @Pattern(regexp = "^[\\p{L}\\d\\s.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                    "pontos, apóstrofos e hífens")
            String neighborhood,

            @Size(max = 100)
            String complement,

            @NotNull
            BigDecimal latitude,

            @NotNull
            BigDecimal longitude
    ) {

    }
}
