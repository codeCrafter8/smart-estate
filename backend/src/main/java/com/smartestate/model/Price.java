package com.smartestate.model;

import com.smartestate.model.enumeration.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "prices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Price {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "price_seq_generator"
    )
    @SequenceGenerator(
            name = "price_seq_generator",
            sequenceName = "price_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;
}
