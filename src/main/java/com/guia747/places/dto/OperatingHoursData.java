package com.guia747.places.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;
import com.guia747.places.vo.OperatingHours;

public record OperatingHoursData(
        @NotNull DayOfWeek dayOfWeek,
        @NotNull LocalTime openTime,
        @NotNull LocalTime closeTime
) {

    public static OperatingHoursData from(OperatingHours operatingHours) {
        return new OperatingHoursData(
                operatingHours.getDayOfWeek(),
                operatingHours.getOpenTime(),
                operatingHours.getCloseTime()
        );
    }
}
