package com.guia747.places.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Contact {

    private String phoneNumber;
    private String instagramUrl;
    private String facebookUrl;
    private String whatsappUrl;

    public static Contact createNew(String phoneNumber, String instagramUrl, String facebookUrl, String whatsappUrl) {
        return new Contact(phoneNumber, instagramUrl, facebookUrl, whatsappUrl);
    }
}
