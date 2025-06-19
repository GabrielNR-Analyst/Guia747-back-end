package com.guia747.infrastructure.persistence.jpa.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PlaceAddressEmbeddable {

    private String zipCode;
    private String street;
    private String number;
    private String neighborhood;
    private String complement;
}
