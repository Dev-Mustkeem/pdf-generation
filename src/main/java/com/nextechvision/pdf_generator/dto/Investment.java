package com.nextechvision.pdf_generator.dto;

import java.math.BigDecimal;

public record Investment(
        String asOfDate,
        String fundName,
        String priceType,
        BigDecimal unitPrice,
        BigDecimal unitsHeld,
        BigDecimal marketValue
) {}