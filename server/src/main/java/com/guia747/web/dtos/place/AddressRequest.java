package com.guia747.web.dtos.place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.guia747.domain.places.valueobject.Address;

public record AddressRequest(
        @NotBlank
        @Size(min = 3, max = 255)
        @Pattern(regexp = "^[\\p{L}\\d\\s\\.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                "pontos, apóstrofos e hífens")
        String street,

        @NotBlank
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Deve ser um CEP válido no formato XXXXX-XXX")
        String zipCode,

        @Size(max = 20)
        @Pattern(regexp = "^[a-zA-Z0-9\\s-]*$", message = "O número do endereço contém caracteres inválidos.")
        String number,

        @NotBlank @Size(min = 3, max = 100)
        @Pattern(regexp = "^[\\p{L}\\d\\s\\.'-]+$", message = "Deve conter apenas letras, números, espaços, " +
                "pontos, apóstrofos e hífens")
        String neighborhood,

        @Size(max = 100)
        String complement
) {

    public static AddressRequest from(Address address) {
        return new AddressRequest(
                address.getStreet(),
                address.getZipCode(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getComplement()
        );
    }
}
