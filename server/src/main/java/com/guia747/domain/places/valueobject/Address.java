package com.guia747.domain.places.valueobject;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Address {

    private String zipCode;
    private String street;
    private String number;
    private String neighborhood;
    private String complement;

    private BigDecimal latitude;
    private BigDecimal longitude;

    public static Address createNew(String zipCode, String street, String number, String neighborhood,
            String complement, BigDecimal latitude, BigDecimal longitude) {
        return new Address(zipCode, street, number, neighborhood, complement, latitude, longitude);
    }
}
