package com.nextechvision.pdf_generator.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.Map;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Generates a PDF from Thymeleaf template and adds page numbers.
     */
    public void generatePdf(Map<String, Object> data) throws Exception {
        Context context = new Context();
        context.setVariables(data);

        // Process Thymeleaf HTML template
        String html = templateEngine.process("statement", context);

        // Step 1: Render HTML to PDF in-memory
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();

        // Base URI for resolving images
        String baseUri = FileSystems.getDefault()
                .getPath("src", "main", "resources", "static")
                .toUri()
                .toString();

        builder.withHtmlContent(html, baseUri);
        builder.toStream(pdfOutputStream);
        builder.run();

        // Step 2: Add page numbers using PDFBox
        try (PDDocument document = PDDocument.load(pdfOutputStream.toByteArray())) {
            int totalPages = document.getNumberOfPages();

            for (int i = 0; i < totalPages; i++) {
                PDPage page = document.getPage(i);
                PDRectangle mediaBox = page.getMediaBox();
                float y = 70; // 70 PX margin from bottom to give space for footer
                float x = mediaBox.getWidth() - 100; // Setting alignment to the right with media query

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(x, y);
                    contentStream.showText("Page " + (i + 1) + " of " + totalPages);
                    contentStream.endText();
                }
            }

            // Step 3: Save final PDF with page numbers
            try (OutputStream os = new FileOutputStream("statement.pdf")) {
                document.save(os);
            }
        }
    }
}
