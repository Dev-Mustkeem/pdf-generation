// In: pdf-generator/src/main/java/com/nextechvision/pdf_generator/service/PdfService.java

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

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generatePdf(Map<String, Object> data) throws Exception {
        Context context = new Context();
        context.setVariables(data);

        String html = templateEngine.process("statement", context);

        try (OutputStream os = new FileOutputStream("statement.pdf")) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();

            // This is the most reliable way to set the base URI for local resources.
            // It correctly points to your project's `resources` directory.
            String baseUri = FileSystems.getDefault()
                    .getPath("src", "main", "resources", "static")
                    .toUri()
                    .toString();

            builder.withHtmlContent(html, baseUri);
            builder.toStream(os);
            builder.run();
        }
    }
}