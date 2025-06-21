package com.guia747.web.dtos.place;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload after successfully creating a new place.")
public record CreatePlaceResponse(
        @Schema(description = "The unique identifier of the newly created place")
        UUID id
) {

}
