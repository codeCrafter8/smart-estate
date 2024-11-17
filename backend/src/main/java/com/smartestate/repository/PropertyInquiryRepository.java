package com.smartestate.repository;

import com.smartestate.model.PropertyInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyInquiryRepository extends JpaRepository<PropertyInquiry, Long> {
    List<PropertyInquiry> findByPropertyId(Long propertyId);
}
