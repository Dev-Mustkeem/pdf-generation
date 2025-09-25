package com.nextechvision.pdf_generator.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.Map;

@Service
public class PdfService {

    /* Thymeleaf template engine for processing HTML templates */
    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Generates a PDF from Thymeleaf template using provided data.
     */
    public void generatePdf(Map<String, Object> data) throws Exception {
        /* Create Thymeleaf context and set template variables */
        Context context = new Context();
        context.setVariables(data);

        /* Process the HTML template */
        String html = templateEngine.process("statement", context);

        try (OutputStream os = new FileOutputStream("statement.pdf")) {
            /* Initialize PDF renderer */
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();

            /* Set base URI for resolving images and other resources */
            String baseUri = FileSystems.getDefault()
                    .getPath("src", "main", "resources", "static")
                    .toUri()
                    .toString();

            builder.withHtmlContent(html, baseUri);
            builder.toStream(os);

            /* Generate PDF */
            builder.run();
        }
    }
}
