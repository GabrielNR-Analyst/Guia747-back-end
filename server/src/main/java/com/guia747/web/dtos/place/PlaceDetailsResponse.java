package com.guia747.web.dtos.place;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import com.guia747.domain.places.entity.Place;
import com.guia747.domain.places.valueobject.Address;
import com.guia747.domain.places.valueobject.Contact;
import com.guia747.domain.places.valueobject.FAQ;
import com.guia747.domain.places.valueobject.OperatingHours;

public record PlaceDetailsResponse(
        UUID id,
        UUID ownerId,
        String name,
        String about,
        AddressResponse address,
        ContactResponse contact,
        String youtubeVideoUrl,
        String thumbnailUrl,
        List<OperatingHoursResponse> operatingHours,
        List<FAQResponse> faqs
) {

    public static PlaceDetailsResponse from(Place place) {
        List<OperatingHoursResponse> operatingHours = place.getOperatingHours()
                .stream()
                .map(OperatingHoursResponse::from)
                .toList();

        List<FAQResponse> faq = place.getFaqs().stream()
                .map(FAQResponse::from)
                .toList();

        return new PlaceDetailsResponse(
                place.getId(),
                place.getUser().getId(),
                place.getName(),
                place.getAbout(),
                AddressResponse.from(place.getAddress()),
                ContactResponse.from(place.getContact()),
                place.getYoutubeVideoUrl(),
                place.getThumbnailUrl(),
                operatingHours,
                faq
        );
    }

    public record FAQResponse(
            String question,
            String answer
    ) {

        public static FAQResponse from(FAQ faq) {
            return new FAQResponse(
                    faq.getQuestion(),
                    faq.getAnswer()
            );
        }
    }

    public record OperatingHoursResponse(
            DayOfWeek dayOfWeek,
            LocalTime openTime,
            LocalTime closeTime
    ) {

        public static OperatingHoursResponse from(OperatingHours operatingHours) {
            return new OperatingHoursResponse(
                    operatingHours.getDayOfWeek(),
                    operatingHours.getOpenTime(),
                    operatingHours.getCloseTime()
            );
        }
    }

    public record ContactResponse(
            String phoneNumber,
            String instagramUrl,
            String facebookUrl,
            String whatsappUrl,
            String email
    ) {

        public static ContactResponse from(Contact contact) {
            return new ContactResponse(
                    contact.getPhoneNumber(),
                    contact.getInstagramUrl(),
                    contact.getFacebookUrl(),
                    contact.getWhatsappUrl(),
                    contact.getEmail()
            );
        }
    }

    public record AddressResponse(
            String street,
            String zipCode,
            String number,
            String neighborhood,
            String complement,
            BigDecimal latitude,
            BigDecimal longitude
    ) {

        public static AddressResponse from(Address address) {
            return new AddressResponse(
                    address.getStreet(),
                    address.getZipCode(),
                    address.getNumber(),
                    address.getNeighborhood(),
                    address.getComplement(),
                    address.getLatitude(),
                    address.getLongitude()
            );
        }
    }
}
