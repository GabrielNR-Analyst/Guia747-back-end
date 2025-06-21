package com.guia747.web.dtos.place;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating a new place")
public record CreatePlaceRequest(
        @NotNull
        @Schema(description = "The unique ID of the city where the place is located.")
        UUID cityId,

        @NotBlank
        @Size(max = 100)
        @Schema(description = "The name of the place", example = "Doce & Companhia")
        String name,

        @Size(max = 500)
        @Schema(
                description = "A short description of the place",
                example = """
                        O melhor lugar da cidade para você tomar um bom café.
                        Fatias de tortas artesanais, bolos, lanches e biscoitos caseiros.
                        """
        )
        String about,

        @NotNull @Valid
        @Schema(description = "Geographical address details of the place")
        CreatePlaceAddressRequest address,

        @Valid
        @Schema(description = "Contact details of the place")
        PlaceContactRequest contact,

        @Valid
        @Schema(description = "List of daily operating hours for the place")
        List<OperatingHoursRequest> operatingHours,
        List<UUID> categoryIds
) {

}
