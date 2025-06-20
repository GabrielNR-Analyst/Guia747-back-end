package com.guia747.infrastructure.persistence.jpa.specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;

public class CitySpecification {

    public static Specification<JpaCityEntity> withFilters(String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String lowerCaseSearch = "%" + search.toLowerCase() + "%";
                Predicate nameLike = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        lowerCaseSearch
                );

                predicates.add(nameLike);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
