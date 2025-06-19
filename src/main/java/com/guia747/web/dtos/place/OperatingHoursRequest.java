package com.guia747.web.dtos.place;

import java.time.DayOfWeek;
import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;
import com.guia747.domain.places.valueobject.OperatingHours;

public record OperatingHoursRequest(
        @NotNull DayOfWeek dayOfWeek,
        @NotNull LocalTime openTime,
        @NotNull LocalTime closeTime
) {

    public static OperatingHoursRequest from(OperatingHours operatingHours) {
        return new OperatingHoursRequest(
                operatingHours.getDayOfWeek(),
                operatingHours.getOpenTime(),
                operatingHours.getCloseTime()
        );
    }
}
