package com.guia747.places.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.accounts.domain.UserRepository;
import com.guia747.accounts.exception.UserNotFoundException;
import com.guia747.cities.entity.City;
import com.guia747.cities.exception.CityNotFoundException;
import com.guia747.cities.repository.CityRepository;
import com.guia747.places.dto.CreatePlaceRequest;
import com.guia747.places.dto.OperatingHoursData;
import com.guia747.places.dto.PlaceDetailsResponse;
import com.guia747.places.dto.UpdatePlaceRequest;
import com.guia747.places.entity.Place;
import com.guia747.places.exception.PlaceNotFoundException;
import com.guia747.places.repository.PlaceRepository;
import com.guia747.places.vo.Address;
import com.guia747.places.vo.Contact;
import com.guia747.places.vo.FAQ;
import com.guia747.places.vo.OperatingHours;

@Service
public class DefaultPlaceManagementService implements PlaceManagementService {

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public DefaultPlaceManagementService(PlaceRepository placeRepository, CityRepository cityRepository,
            UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Place createPlace(UUID userId, CreatePlaceRequest request) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        City city = cityRepository.findById(request.cityId()).orElseThrow(CityNotFoundException::new);

        Address address = Address.createNew(
                request.address().zipCode(),
                request.address().street(),
                request.address().number(),
                request.address().neighborhood(),
                request.address().complement()
        );

        Place place = Place.createNew(userAccount, city, request.name(), request.about(), address);

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
            // TODO
        }

        return placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceDetailsResponse getPlaceDetail(UUID placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        return PlaceDetailsResponse.from(place);
    }

    @Override
    @Transactional
    public void updatePlace(UUID placeId, UpdatePlaceRequest request) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        // Update basic info
        place.updateBasicInfo(request.name(), request.about());

        // Update address
        if (request.address() != null) {
            Address address = createAddress(request.address());
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

        place.updateMedia(request.youtubeVideoUrl(), request.thumbnailUrl());
        placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> getAllPlacesByCity(UUID cityId, PageRequest pageable) {
        cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        return placeRepository.findByCityId(cityId, pageable);
    }

    private static Address createAddress(UpdatePlaceRequest.AddressData address) {
        return Address.createNew(
                address.zipCode(),
                address.street(),
                address.number(),
                address.neighborhood(),
                address.complement()
        );
    }

    private OperatingHours createOperatingHours(OperatingHoursData data) {
        return OperatingHours.createNew(data.dayOfWeek(), data.openTime(), data.closeTime());
    }
}
