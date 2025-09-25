package com.nextechvision.pdf_generator.controller;

import com.nextechvision.pdf_generator.dto.Investment;
import com.nextechvision.pdf_generator.dto.Transaction;
import com.nextechvision.pdf_generator.service.PdfService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        /* Statement details for the investor */
        data.put("investorName", "Mustkeem Baraskar");
        data.put("investorAddress", new String[]{
                "202 Imperial Plaza", "Kothrud", "7 Streets", "New York"
        });
        data.put("investorCode", "PCROSSCAT");
        data.put("adviser", "Lopez (AD0000790001)");
        data.put("statementPeriod", "1 June 2024 - 1 October 2024");

        /* Generate multiple investments to test multi-page PDF */
        List<Investment> investments = new ArrayList<>();
        BigDecimal totalInvestment = BigDecimal.ZERO;
        for (int i = 1; i <= 25; i++) {
            BigDecimal unitPrice = new BigDecimal("1.5").add(new BigDecimal(i).multiply(new BigDecimal("0.01")));
            BigDecimal unitsHeld = new BigDecimal("1000").add(new BigDecimal(i).multiply(new BigDecimal("50")));
            BigDecimal marketValue = unitPrice.multiply(unitsHeld);
            totalInvestment = totalInvestment.add(marketValue);

            investments.add(new Investment(
                    "01/10/2024",
                    "Investment Fund " + i,
                    "RED",
                    unitPrice,
                    unitsHeld,
                    marketValue
            ));
        }
        data.put("investments", investments);
        data.put("totalInvestment", totalInvestment);

        /* Generate multiple transactions to test multi-page PDF */
        List<Transaction> transactions = getTransactions();
        data.put("fundName", "Various Investment Funds");
        data.put("transactions", transactions);

        /* Generate PDF using PdfService */
        pdfService.generatePdf(data);

        return "PDF generated! Check project root for statement.pdf";
    }

    /* Create mock transactions with varying market values, unit prices, and balances */
    private static List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        BigDecimal balance = new BigDecimal("5000");
        for (int i = 1; i <= 30; i++) {
            BigDecimal marketValue = balance.add(new BigDecimal(i * 100));
            BigDecimal unitPrice = new BigDecimal("1.5").add(new BigDecimal(i).multiply(new BigDecimal("0.01")));
            BigDecimal units = new BigDecimal("1000").add(new BigDecimal(i * 10));
            balance = units; /* simulate unit balance */

            transactions.add(new Transaction(
                    "01/" + String.format("%02d", i) + "/2024",
                    "Transaction " + i,
                    marketValue,
                    "RED",
                    unitPrice,
                    units,
                    balance
            ));
        }
        return transactions;
    }
}
