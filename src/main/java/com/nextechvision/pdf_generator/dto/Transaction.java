package com.nextechvision.pdf_generator.dto;

import java.math.BigDecimal;

public record Transaction(
        String date,
        String type,
        BigDecimal marketValue,
        String priceType,
        BigDecimal unitPrice,
        BigDecimal units,
        BigDecimal balance
) {}