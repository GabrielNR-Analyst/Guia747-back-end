package com.guia747.application.place.usecase.impl;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.place.usecase.CreatePlaceUseCase;
import com.guia747.domain.users.entity.User;
import com.guia747.domain.users.repository.UserRepository;
import com.guia747.domain.users.exception.UserNotFoundException;
import com.guia747.domain.city.entity.City;
import com.guia747.domain.city.exception.CityNotFoundException;
import com.guia747.domain.city.repository.CityRepository;
import com.guia747.domain.places.entity.Category;
import com.guia747.domain.places.entity.Place;
import com.guia747.domain.places.exception.CategoryNotFoundException;
import com.guia747.domain.places.repository.CategoryRepository;
import com.guia747.domain.places.repository.PlaceRepository;
import com.guia747.domain.places.valueobject.Address;
import com.guia747.domain.places.valueobject.Contact;
import com.guia747.domain.places.valueobject.OperatingHours;
import com.guia747.web.dtos.place.CreatePlaceRequest;
import com.guia747.web.dtos.place.CreatePlaceResponse;
import com.guia747.web.dtos.place.OperatingHoursRequest;

@Service
public class DefaultCreatePlaceUseCase implements CreatePlaceUseCase {

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public DefaultCreatePlaceUseCase(
            PlaceRepository placeRepository,
            CityRepository cityRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.placeRepository = placeRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CreatePlaceResponse execute(UUID ownerId, CreatePlaceRequest request) {
        User user = userRepository.findById(ownerId).orElseThrow(UserNotFoundException::new);
        City city = cityRepository.findById(request.cityId()).orElseThrow(CityNotFoundException::new);

        Address address = Address.createNew(
                request.address().zipCode(),
                request.address().street(),
                request.address().number(),
                request.address().neighborhood(),
                request.address().complement()
        );

        Place place = Place.createNew(user, city, request.name(), request.about(), address);

        // Update contact
        if (request.contact() != null) {
            Contact contact = Contact.createNew(
                    request.contact().phoneNumber(),
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

        // Update categories
        if (request.categoryIds() != null) {
            List<Category> categories = categoryRepository.findAllByIdIn(request.categoryIds());

            if (categories.size() != request.categoryIds().size()) {
                throw new CategoryNotFoundException();
            }

            place.updateCategories(new HashSet<>(categories));
        }

        Place savedPlace = placeRepository.save(place);
        return new CreatePlaceResponse(savedPlace.getId());
    }

    private OperatingHours createOperatingHours(OperatingHoursRequest data) {
        return OperatingHours.createNew(data.dayOfWeek(), data.openTime(), data.closeTime());
    }
}
