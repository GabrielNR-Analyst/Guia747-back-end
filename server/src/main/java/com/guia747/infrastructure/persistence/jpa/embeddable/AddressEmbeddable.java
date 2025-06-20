package com.guia747.infrastructure.persistence.jpa.embeddable;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AddressEmbeddable {

    @Column(name = "zip_code", length = 10, nullable = false)
    private String zipCode;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "neighborhood", nullable = false, length = 100)
    private String neighborhood;

    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;
}
