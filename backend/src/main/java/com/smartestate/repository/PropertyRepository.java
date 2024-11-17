package com.smartestate.repository;

import com.smartestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>,
        JpaSpecificationExecutor<Property> {
    List<Property> findByUserId(Long id);
}
