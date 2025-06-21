package com.guia747.web.dtos.place;

import java.math.BigDecimal;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Geographical address details of the place.")
public record UpdatePlaceAddressRequest(
        @Size(min = 3, max = 255)
        @Pattern(regexp = "^[\\p{L}\\d\\s.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                "pontos, apóstrofos e hífens")
        @Schema(
                description = "The street name of the address",
                example = "Rua 7 de Setembro"
        )
        String street,

        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Deve ser um CEP válido no formato XXXXX-XXX")
        @Schema(
                description = "The ZIP code (CEP) in format XXXXX-XXX",
                example = "89160-170"
        )
        String zipCode,

        @Size(max = 20)
        @Pattern(regexp = "^[a-zA-Z0-9\\s-]*$", message = "O número do endereço contém caracteres inválidos.")
        @Schema(
                description = "The building number",
                example = "319"
        )
        String number,

        @Size(min = 3, max = 100)
        @Pattern(regexp = "^[\\p{L}\\d\\s.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                "pontos, apóstrofos e hífens")
        @Schema(
                description = "The neighborhood name",
                example = "Liberdade Centro"
        )
        String neighborhood,

        @Size(max = 100)
        @Schema(
                description = "Additional address information, like apartment or suite number"
        )
        String complement,

        @Schema(description = "The geographical latitude of the place's address", example = "-23.550520")
        BigDecimal latitude,

        @Schema(description = "The geographical longitude of the place's address", example = "-46.633308")
        BigDecimal longitude
) {

}
