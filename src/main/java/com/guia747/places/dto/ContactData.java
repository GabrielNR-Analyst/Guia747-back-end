package com.guia747.places.dto;

import jakarta.validation.constraints.Size;
import com.guia747.places.vo.Contact;

public record ContactData(
        @Size(max = 20) String phoneNumber,
        @Size(max = 200) String instagramUrl,
        @Size(max = 200) String facebookUrl,
        @Size(max = 200) String whatsappUrl
) {

    public static ContactData from(Contact contact) {
        return new ContactData(
                contact.getPhoneNumber(),
                contact.getInstagramUrl(),
                contact.getFacebookUrl(),
                contact.getWhatsappUrl()
        );
    }
}
