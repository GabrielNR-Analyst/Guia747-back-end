package com.guia747.places.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import com.guia747.places.vo.OperatingHours;

public record OperatingHoursData(
        DayOfWeek dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime
) {

    public static OperatingHoursData from(OperatingHours operatingHours) {
        return new OperatingHoursData(
                operatingHours.getDayOfWeek(),
                operatingHours.getOpenTime(),
                operatingHours.getCloseTime()
        );
    }
}
