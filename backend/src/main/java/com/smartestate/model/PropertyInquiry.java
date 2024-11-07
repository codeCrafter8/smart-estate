package com.smartestate.model;

import com.smartestate.model.enumeration.InquiryStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "property_inquiries")
public class PropertyInquiry {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inquiry_seq_generator"
    )
    @SequenceGenerator(
            name = "inquiry_seq_generator",
            sequenceName = "inquiry_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status = InquiryStatus.PENDING;
}
