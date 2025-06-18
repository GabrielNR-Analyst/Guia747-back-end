package com.guia747.places.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {

    private String zipCode;
    private String street;
    private String number;
    private String neighborhood;
    private String complement;

    public static Address createNew(String zipCode, String street, String number, String neighborhood,
            String complement) {
        return new Address(zipCode, street, number, neighborhood, complement);
    }
}
