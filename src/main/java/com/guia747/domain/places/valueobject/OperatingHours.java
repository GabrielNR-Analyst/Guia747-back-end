package com.guia747.domain.places.valueobject;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class OperatingHours {

    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;

    public static OperatingHours createNew(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {
        return new OperatingHours(dayOfWeek, openTime, closeTime);
    }
}
