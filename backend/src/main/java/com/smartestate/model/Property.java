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

    private Integer yearBuilt;

    private Integer totalBuildingFloors;

    private Integer apartmentFloor;

    private Integer totalRooms;

    private Integer totalBedrooms;

    private Integer totalBathrooms;

    @Column(nullable = false)
    private BigDecimal area;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Image> images;

    @OneToMany(
            mappedBy = "property",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<PropertyInquiry> inquiries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;
}
