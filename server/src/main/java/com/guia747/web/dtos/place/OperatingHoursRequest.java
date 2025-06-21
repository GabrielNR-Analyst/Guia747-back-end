package com.guia747.web.dtos.place;

import java.time.DayOfWeek;
import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;
import com.guia747.domain.places.valueobject.OperatingHours;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the operating hours for a specific day of the week")
public record OperatingHoursRequest(
        @NotNull
        @Schema(description = "Day of the week for the operating hours", example = "MONDAY")
        DayOfWeek dayOfWeek,

        @NotNull
        @Schema(description = "Opening time in HH:mm:ss format", example = "09:00")
        LocalTime openTime,

        @NotNull
        @Schema(description = "Closing time in HH:mm:ss format", example = "18:00")
        LocalTime closeTime
) {

    public static OperatingHoursRequest from(OperatingHours operatingHours) {
        return new OperatingHoursRequest(
                operatingHours.getDayOfWeek(),
                operatingHours.getOpenTime(),
                operatingHours.getCloseTime()
        );
    }
}
