package com.smartestate.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "location_seq_generator"
    )
    @SequenceGenerator(
            name = "location_seq_generator",
            sequenceName = "location_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false)
    private String address;
}

