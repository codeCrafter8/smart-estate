package com.smartestate.service;

import com.smartestate.model.Location;
import com.smartestate.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public Location addLocation(String address, String country) {
        Location location = Location.builder()
                .address(address)
                .country(country)
                .build();

        log.info("Adding location: {}", location);
        return locationRepository.save(location);
    }
}
