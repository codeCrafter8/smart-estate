package com.smartestate.service;

import com.smartestate.model.Price;
import com.smartestate.model.enumeration.Currency;
import com.smartestate.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public Price addPrice(BigDecimal priceAmount, String currency) {
        Price price = Price.builder()
                .amount(priceAmount)
                .currency(Currency
                        .valueOf(currency.toUpperCase()))
                .build();

        log.info("Adding priceAmount: {}", price);
        return priceRepository.save(price);
    }
}
