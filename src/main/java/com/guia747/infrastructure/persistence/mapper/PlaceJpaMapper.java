package com.guia747.infrastructure.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.embeddable.AddressEmbeddable;
import com.guia747.infrastructure.persistence.jpa.embeddable.FAQEmbeddable;
import com.guia747.infrastructure.persistence.jpa.embeddable.OperatingHoursEmbeddable;
import com.guia747.infrastructure.persistence.jpa.embeddable.ContactEmbeddable;
import com.guia747.infrastructure.persistence.jpa.entity.JpaPlaceEntity;
import com.guia747.places.entity.Place;
import com.guia747.places.vo.Address;
import com.guia747.places.vo.Contact;
import com.guia747.places.vo.FAQ;
import com.guia747.places.vo.OperatingHours;

@Mapper(config = GlobalMapperConfig.class, uses = {UserMapper.class, CategoryJpaMapper.class, CityMapper.class})
public interface PlaceJpaMapper {

    AddressEmbeddable toAddressEmbeddable(Address address);

    Address toAddress(AddressEmbeddable addressEmbeddable);

    ContactEmbeddable toContactEmbeddable(Contact contact);

    Contact toContact(ContactEmbeddable contactEmbeddable);

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
