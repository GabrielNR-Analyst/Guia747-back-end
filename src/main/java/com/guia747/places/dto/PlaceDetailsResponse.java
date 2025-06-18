package com.guia747.places.dto;

import java.util.List;
import java.util.UUID;
import com.guia747.places.entity.Place;

public record PlaceDetailsResponse(
        UUID id,
        UUID ownerId,
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
                .stream().map(OperatingHoursData::from).toList();

        List<FAQData> faq = place.getFaqs().stream()
                .map(FAQData::from)
                .toList();

        return new PlaceDetailsResponse(
                place.getId(),
                place.getUser().getId(),
                place.getAbout(),
                AddressData.from(place.getAddress()),
                ContactData.from(place.getContact()),
                place.getYoutubeVideoUrl(),
                place.getThumbnailUrl(),
                operatingHours,
                faq
        );
    }
}
