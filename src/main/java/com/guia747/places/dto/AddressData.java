package com.guia747.places.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.guia747.places.vo.Address;

public record AddressData(
        @NotBlank @Size(max = 200) String street,
        @Size(max = 20) String zipCode,
        @Size(max = 20) String number,
        @NotBlank @Size(max = 100) String neighborhood,
        @Size(max = 100) String complement
) {

    public static AddressData from(Address address) {
        return new AddressData(
                address.getStreet(),
                address.getZipCode(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getComplement()
        );
    }
}
