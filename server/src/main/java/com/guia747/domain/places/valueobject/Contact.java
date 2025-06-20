package com.guia747.domain.places.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Contact {

    private String phoneNumber;
    private String instagramUrl;
    private String facebookUrl;
    private String whatsappUrl;
    private String email;

    public static Contact createNew(String phoneNumber, String instagramUrl, String facebookUrl, String whatsappUrl,
            String email) {
        return new Contact(phoneNumber, instagramUrl, facebookUrl, whatsappUrl, email);
    }
}
