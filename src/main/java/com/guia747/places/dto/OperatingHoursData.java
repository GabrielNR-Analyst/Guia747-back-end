package com.guia747.places.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OperatingHoursData(
        DayOfWeek dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime
) {

}
