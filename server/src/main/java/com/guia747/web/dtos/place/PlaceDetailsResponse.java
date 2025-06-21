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
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed information about a place (establishment).")
public record PlaceDetailsResponse(
        @Schema(description = "Unique identifier of the place")
        UUID id,
        @Schema(description = "Unique identifier of the owner's account")
        UUID ownerId,
        @Schema(description = "TName of the place ")
        String name,
        @Schema(description = "A short description of the place")
        String about,
        @Schema(description = "Address details of the place")
        AddressResponse address,
        @Schema(description = "Contact details of the place")
        ContactResponse contact,
        @Schema(description = "URL of a YouTube video showcasing the place")
        String youtubeVideoUrl,
        @Schema(description = "URL of the place's thumbnail image")
        String thumbnailUrl,
        @Schema(description = "List of operating hours for each day of the week.")
        List<OperatingHoursResponse> operatingHours,
        @Schema(description = "List of Frequently Asked Questions (FAQs) related to the place.")
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

    @Schema(description = "A Frequently Asked Question (FAQ) and its answer.")
    public record FAQResponse(
            @Schema(
                    description = "The question of the FAQ.",
                    example = "Vocês têm opções veganas?"
            )
            String question,

            @Schema(
                    description = "The answer to the FAQ",
                    example = "Sim, oferecemos uma variedade de bolos veganos e alternativas ao leite à base de plantas"
            )
            String answer
    ) {

        public static FAQResponse from(FAQ faq) {
            return new FAQResponse(
                    faq.getQuestion(),
                    faq.getAnswer()
            );
        }
    }

    @Schema(description = "Represents the operating hours for a specific day of the week")
    public record OperatingHoursResponse(
            @Schema(description = "Day of the week for the operating hours", example = "MONDAY")
            DayOfWeek dayOfWeek,
            @Schema(description = "Opening time in HH:mm:ss format", example = "09:00")
            LocalTime openTime,
            @Schema(description = "Closing time in HH:mm:ss format", example = "18:00")
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

    @Schema(description = "Contact information for the place")
    public record ContactResponse(
            @Schema(description = "The phone number of the place", example = "(11) 99999-9999")
            String phoneNumber,

            @Schema(
                    description = "URL to the place's Instagram profile",
                    example = "https://www.instagram.com/docecompanhia/"
            )
            String instagramUrl,

            @Schema(
                    description = "URL to the place's Facebook page",
                    example = "https://www.facebook.com/docecompanhia/"
            )
            String facebookUrl,

            @Schema(
                    description = "URL to the place's WhatsApp contact.",
                    example = "https://wa.me/5511987654321"
            )
            String whatsappUrl,

            @Schema(description = "The email address of the place", example = "contato@docecompanhia.com")
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

    @Schema(description = "Geographical address details of the place.")
    public record AddressResponse(
            @Schema(
                    description = "The street name of the address",
                    example = "Rua 7 de Setembro"
            )
            String street,

            @Schema(
                    description = "The ZIP code (CEP) in format XXXXX-XXX",
                    example = "89160-170"
            )
            String zipCode,

            @Schema(
                    description = "The building number",
                    example = "319"
            )
            String number,

            @Schema(
                    description = "The neighborhood name",
                    example = "Liberdade Centro"
            )
            String neighborhood,

            @Schema(
                    description = "Additional address information, like apartment or suite number", example = " "
            )
            String complement,

            @Schema(description = "The geographical latitude of the place's address", example = "-23.550520")
            BigDecimal latitude,

            @Schema(description = "The geographical longitude of the place's address", example = "-46.633308")
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
