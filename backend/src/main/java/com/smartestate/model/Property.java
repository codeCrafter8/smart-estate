package com.smartestate.model;

import com.smartestate.model.enumeration.CountryName;
import com.smartestate.model.enumeration.PropertyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "properties")
@Entity
public class Property {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "property_seq_generator"
    )
    @SequenceGenerator(
            name = "property_seq_generator",
            sequenceName = "property_sequence",
            allocationSize = 1
    )
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private CountryName countryName;

    @Column(nullable = false)
    private String regionName;

    private Integer yearBuilt;

    private Integer totalBuildingFloors;

    private Integer apartmentFloor;

    private Integer totalRooms;

    private Integer totalBedrooms;

    private Integer totalBathrooms;

    private BigDecimal apartmentArea;

    private BigDecimal priceInUsd;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Image> images;
}
