package com.smartestate.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_seq_generator"
    )
    @SequenceGenerator(
            name = "image_seq_generator",
            sequenceName = "image_sequence",
            allocationSize = 1
    )
    private Integer id;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
