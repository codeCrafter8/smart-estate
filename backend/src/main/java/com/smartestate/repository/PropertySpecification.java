package com.smartestate.repository;

import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.model.Property;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecification {
    public static Specification<Property> filterByCriteria(PropertySearchCriteriaDto criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (criteria.propertyType() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("propertyType"), criteria.propertyType()));
            }

            if (criteria.location() != null && !criteria.location().isBlank()) {
                String likePattern = likePattern(criteria.location());

                Join<Object, Object> locationJoin = root.join("location");
                Predicate locationPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(locationJoin.get("country"), likePattern),
                        criteriaBuilder.like(locationJoin.get("address"), likePattern)
                );
                predicate = criteriaBuilder.and(predicate, locationPredicate);
            }

            if (criteria.minPrice() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.minPrice()));
            }
            if (criteria.maxPrice() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.maxPrice()));
            }

            if (criteria.currency() != null && !criteria.currency().isBlank()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("currency"), criteria.currency()));
            }

            if (criteria.minArea() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("area"), criteria.minArea()));
            }
            if (criteria.maxArea() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("area"), criteria.maxArea()));
            }

            return predicate;
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }
}
