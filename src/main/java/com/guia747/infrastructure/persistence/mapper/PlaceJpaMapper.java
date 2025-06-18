package com.guia747.infrastructure.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.FAQEmbeddable;
import com.guia747.infrastructure.persistence.jpa.entity.OperatingHoursEmbeddable;
import com.guia747.infrastructure.persistence.jpa.entity.PlaceAddressEmbeddable;
import com.guia747.infrastructure.persistence.jpa.entity.PlaceContactEmbeddable;
import com.guia747.infrastructure.persistence.jpa.entity.JpaPlaceEntity;
import com.guia747.places.entity.Place;
import com.guia747.places.vo.Address;
import com.guia747.places.vo.Contact;
import com.guia747.places.vo.FAQ;
import com.guia747.places.vo.OperatingHours;

@Mapper(config = GlobalMapperConfig.class, uses = {UserMapper.class, CategoryJpaMapper.class, CityMapper.class})
public interface PlaceJpaMapper {

    PlaceAddressEmbeddable toAddressEmbeddable(Address address);

    Address toAddress(PlaceAddressEmbeddable addressEmbeddable);

    PlaceContactEmbeddable toContactEmbeddable(Contact contact);

    Contact toContact(PlaceContactEmbeddable contactEmbeddable);

    OperatingHoursEmbeddable toOperatingHoursEmbeddable(OperatingHours operatingHours);

    OperatingHours toOperatingHours(OperatingHoursEmbeddable operatingHoursEmbeddable);

    List<OperatingHoursEmbeddable> toOperatingHoursEmbeddableList(List<OperatingHours> operatingHoursList);

    List<OperatingHours> toOperatingHoursDomainList(List<OperatingHoursEmbeddable> embeddableList);

    FAQEmbeddable toFAQEmbeddable(FAQ faq);

    FAQ toFAQDomain(FAQEmbeddable embeddable);

    List<FAQEmbeddable> toFAQEmbeddableList(List<FAQ> faqList);

    List<FAQ> toFAQDomainList(List<FAQEmbeddable> embeddableList);

    Place toDomain(JpaPlaceEntity entity);

    JpaPlaceEntity toEntity(Place place);
}
