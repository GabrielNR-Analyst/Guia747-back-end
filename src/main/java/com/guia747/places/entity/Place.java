package com.guia747.places.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.cities.entity.City;
import com.guia747.common.AggregateRoot;
import com.guia747.places.vo.Address;
import com.guia747.places.vo.Contact;
import com.guia747.places.vo.FAQ;
import com.guia747.places.vo.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Place extends AggregateRoot<UUID> {

    private UserAccount user;
    private City city;

    private String name;
    private String about;
    private Address address;
    private Contact contact;

    private String youtubeVideoUrl;
    private String thumbnailUrl;

    private List<OperatingHours> operatingHours;
    private Set<Category> categories;
    private List<FAQ> faqs;

    public static Place createNew(UserAccount user, City city, String name, String about, Address address) {
        return new Place(user, city, name, about, address, null, null, null, new ArrayList<>(), new HashSet<>(),
                new ArrayList<>());
    }

    public void updateOperatingHours(List<OperatingHours> operatingHours) {
        if (operatingHours != null) {
            this.operatingHours.addAll(operatingHours);
        }
    }

    public void updateFaqs(List<FAQ> faqs) {
        this.faqs.clear();
        if (faqs != null) {
            this.faqs.addAll(faqs);
        }
    }

    public void updateContact(Contact contact) {
        if (contact != null) {
            this.contact = contact;
        }
    }

    public void updateMedia(String youtubeVideoUrl, String thumbnailUrl) {
        if (youtubeVideoUrl != null) {
            this.youtubeVideoUrl = youtubeVideoUrl;
        }
        if (thumbnailUrl != null) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }
}
