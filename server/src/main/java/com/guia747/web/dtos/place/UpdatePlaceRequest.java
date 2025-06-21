package com.guia747.web.dtos.place;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating a new place")
public record UpdatePlaceRequest(
        @Schema(description = "The name of the place", example = "Doce & Companhia")
        String name,

        @Schema(
                description = "A short description of the place",
                example = """
                        O melhor lugar da cidade para você tomar um bom café.
                        Fatias de tortas artesanais, bolos, lanches e biscoitos caseiros.
                        """
        )
        String about,

        @Valid
        @Schema(description = "Geographical address details of the place")
        UpdatePlaceAddressRequest address,

        @Valid
        @Schema(description = "Contact details of the place")
        PlaceContactRequest contact,

        @Size(max = 255)
        @Schema(
                description = "URL to a YouTube video promoting the place",
                example = "https://www.youtube.com/watch?v=video_id"
        )
        String youtubeVideoUrl,

        @Size(max = 255)
        @Schema(
                description = "URL to the place's thumbnail image"
        )
        String thumbnailUrl,

        @Valid
        @Schema(description = "List of daily operating hours for the place")
        List<OperatingHoursRequest> operatingHours,

        @Valid
        @Schema(description = "List of updated Frequently Asked Questions (FAQs)")
        List<UpdatePlaceFAQRequest> faqs,

        @Schema(
                description = "List of unique identifiers for categories associated with this place. " +
                        "This will replace existing categories"
        )
        List<UUID> categoryIds
) {

}
