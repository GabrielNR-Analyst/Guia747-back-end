package com.guia747.places.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import com.guia747.places.vo.Contact;

public record ContactData(
        @Size(max = 20) String phoneNumber,
        @Size(max = 200) @URL String instagramUrl,
        @Size(max = 200) @URL String facebookUrl,
        @Size(max = 200) @URL String whatsappUrl,
        @Size(max = 255) @Email String email
) {

    public static ContactData from(Contact contact) {
        return new ContactData(
                contact.getPhoneNumber(),
                contact.getInstagramUrl(),
                contact.getFacebookUrl(),
                contact.getWhatsappUrl(),
                contact.getEmail()
        );
    }
}
