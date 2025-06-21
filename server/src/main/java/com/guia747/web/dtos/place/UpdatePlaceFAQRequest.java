package com.guia747.web.dtos.place;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A Frequently Asked Question and its answer")
public record UpdatePlaceFAQRequest(
        @NotBlank
        @Schema(description = "The question text", example = "Qual o horário de funcionamento?")
        String question,

        @NotBlank
        @Schema(
                description = "The answer text to the question",
                example = "Funcionamos de segunda a sexta, das 8h às 18h"
        )
        String answer
) {

}
