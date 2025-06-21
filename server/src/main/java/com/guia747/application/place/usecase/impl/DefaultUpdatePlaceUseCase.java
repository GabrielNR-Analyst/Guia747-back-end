package com.guia747.application.place.usecase.impl;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.place.usecase.UpdatePlaceUseCase;
import com.guia747.domain.places.entity.Category;
import com.guia747.domain.places.entity.Place;
import com.guia747.domain.places.exception.CategoryNotFoundException;
import com.guia747.domain.places.exception.PlaceNotFoundException;
import com.guia747.domain.places.repository.CategoryRepository;
import com.guia747.domain.places.repository.PlaceRepository;
import com.guia747.domain.places.valueobject.Address;
import com.guia747.domain.places.valueobject.Contact;
import com.guia747.domain.places.valueobject.FAQ;
import com.guia747.domain.places.valueobject.OperatingHours;
import com.guia747.web.dtos.place.OperatingHoursRequest;
import com.guia747.web.dtos.place.UpdatePlaceRequest;

@Service
public class DefaultUpdatePlaceUseCase implements UpdatePlaceUseCase {

    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;

    public DefaultUpdatePlaceUseCase(PlaceRepository placeRepository, CategoryRepository categoryRepository) {
        this.placeRepository = placeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void execute(UUID placeId, UpdatePlaceRequest request) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        // Update basic info
        place.updateBasicInfo(request.name(), request.about());

        // Update address
        if (request.address() != null) {
            Address address = Address.createNew(
                    request.address().zipCode(),
                    request.address().street(),
                    request.address().number(),
                    request.address().neighborhood(),
                    request.address().complement()
            );

            place.updateAddress(address);
        }

        // Update contact
        if (request.contact() != null) {
            Contact contact = Contact.createNew(
                    request.contact().phone(),
                    request.contact().instagramUrl(),
                    request.contact().facebookUrl(),
                    request.contact().whatsappUrl(),
                    request.contact().email()
            );
            place.updateContact(contact);
        }

        // Update operating hours
        if (request.operatingHours() != null) {
            List<OperatingHours> operatingHours = request.operatingHours().stream()
                    .map(this::createOperatingHours)
                    .toList();
            place.updateOperatingHours(operatingHours);
        }

        // Update faqs
        if (request.faqs() != null) {
            List<FAQ> faqs = request.faqs().stream()
                    .map(faq -> FAQ.createNew(faq.question(), faq.answer()))
                    .toList();
            place.updateFaqs(faqs);
        }

        if (request.categoryIds() != null) {
            List<Category> categories = categoryRepository.findAllByIdIn(request.categoryIds());

            if (categories.size() != request.categoryIds().size()) {
                throw new CategoryNotFoundException();
            }

            place.updateCategories(new HashSet<>(categories));
        }

        place.updateMedia(request.youtubeVideoUrl(), request.thumbnailUrl());
        placeRepository.save(place);
    }

    private OperatingHours createOperatingHours(OperatingHoursRequest data) {
        return OperatingHours.createNew(data.dayOfWeek(), data.openTime(), data.closeTime());
    }
}
