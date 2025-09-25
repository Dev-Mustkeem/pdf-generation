package com.nextechvision.pdf_generator.controller;

import com.nextechvision.pdf_generator.dto.Investment;
import com.nextechvision.pdf_generator.dto.Transaction;
import com.nextechvision.pdf_generator.service.PdfService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/generate-pdf")
    public String generatePdf() throws Exception {
        Map<String, Object> data = new HashMap<>();

        // Statement Details
        data.put("investorName", "Mustkeem Baraskar");
        data.put("investorAddress", new String[]{"202 Imperial Plaza", "Kothrud", "7 Streets", "New York"});
        data.put("investorCode", "PCROSSCAT");
        data.put("adviser", "Lopez (AD0000790001)");
        data.put("statementPeriod", "1 June 2024 - 1 October 2024");

        // Investment Summary
        List<Investment> investments = List.of(
                new Investment(
                        "01/10/2024",
                        "Allen Masked Investment Fund",
                        "RED",
                        new BigDecimal("1.8662"),
                        new BigDecimal("138916.95"),
                        new BigDecimal("259246.81")
                )
        );
        data.put("investments", investments);
        data.put("totalInvestment", new BigDecimal("259246.81"));


        // Transaction Details
        data.put("fundName", "Allen Masked Investment Fund");
        List<Transaction> transactions = List.of(
                new Transaction(
                        "01/06/2024", "Opening Balance", null, null, null, null, new BigDecimal("138916.95")
                ),
                new Transaction(
                        "01/07/2024", "Total Distribution (Cash)", new BigDecimal("28182.08"), null, null, null, new BigDecimal("138916.95")
                ),
                new Transaction(
                        "01/10/2024", "Closing Balance", new BigDecimal("259246.81"), "RED", new BigDecimal("1.8662"), new BigDecimal("138916.95"), new BigDecimal("138916.95")
                )
        );
        data.put("transactions", transactions);

        pdfService.generatePdf(data);

        return "PDF generated! Check project root for statement.pdf";
    }
}