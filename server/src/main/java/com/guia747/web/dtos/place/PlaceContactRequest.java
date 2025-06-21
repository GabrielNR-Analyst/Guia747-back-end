package com.guia747.web.dtos.place;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contact information for the place")
public record PlaceContactRequest(
        @Size(max = 20)
        @Schema(description = "The phone number of the place", example = "(11) 99999-9999")
        String phoneNumber,

        @Size(max = 200)
        @URL
        @Schema(
                description = "URL to the place's Instagram profile",
                example = "https://www.instagram.com/docecompanhia/"
        )
        String instagramUrl,

        @Size(max = 200)
        @URL
        @Schema(
                description = "URL to the place's Facebook page",
                example = "https://www.facebook.com/docecompanhia/"
        )
        String facebookUrl,

        @Size(max = 200)
        @URL
        @Schema(
                description = "URL to the place's WhatsApp contact.",
                example = "https://wa.me/5511987654321"
        )
        String whatsappUrl,

        @Size(max = 255)
        @Email
        @Schema(description = "The email address of the place", example = "contato@docecompanhia.com")
        String email
) {

}
