package com.smartestate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String countryName;

    @Column(nullable = false)
    private String regionName;

    private Integer yearBuilt;

    private Integer totalBuildingFloors;

    private Integer apartmentFloor;

    private Integer totalRooms;

    private Integer totalBedrooms;

    private Integer totalBathrooms;

    @Column(nullable = false)
    private BigDecimal apartmentArea;

    @Column(nullable = false)
    private BigDecimal priceInUsd;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Image> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
