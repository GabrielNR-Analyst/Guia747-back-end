package com.guia747.places.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import com.guia747.places.entity.Place;
import com.guia747.places.vo.FAQ;

public record PlaceDetailsResponse(
        UUID id,
        UUID ownerId,
        String name,
        String about,
        AddressData address,
        ContactData contact,
        String youtubeVideoUrl,
        String thumbnailUrl,
        List<OperatingHoursData> operatingHours,
        List<FAQData> faq
) {

    public static PlaceDetailsResponse from(Place place) {
        List<OperatingHoursData> operatingHours = place.getOperatingHours()
                .stream()
                .map(data -> new OperatingHoursData(
                        data.getDayOfWeek(),
                        data.getOpenTime(),
                        data.getCloseTime()
                ))
                .toList();

        List<FAQData> faq = place.getFaqs().stream()
                .map(FAQData::from)
                .toList();

        return new PlaceDetailsResponse(
                place.getId(),
                place.getUser().getId(),
                place.getName(),
                place.getAbout(),
                AddressData.from(place.getAddress()),
                ContactData.from(place.getContact()),
                place.getYoutubeVideoUrl(),
                place.getThumbnailUrl(),
                operatingHours,
                faq
        );
    }

    public record FAQData(
            String question,
            String answer
    ) {

        public static FAQData from(FAQ faq) {
            return new FAQData(
                    faq.getQuestion(),
                    faq.getAnswer()
            );
        }
    }

    public record OperatingHoursData(
            DayOfWeek dayOfWeek,
            LocalTime openTime,
            LocalTime closeTime
    ) {

    }

    public record ContactData(
            String phoneNumber,
            String instagramUrl,
            String facebookUrl,
            String whatsappUrl,
            String email
    ) {

        public static ContactData from(com.guia747.places.vo.Contact contact) {
            return new ContactData(
                    contact.getPhoneNumber(),
                    contact.getInstagramUrl(),
                    contact.getFacebookUrl(),
                    contact.getWhatsappUrl(),
                    contact.getEmail()
            );
        }
    }
}
