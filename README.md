# PDF Generation


## Explanation

### 1. HTML Template
I created a **Thymeleaf HTML template** (`statement.html`) which contains:
- A **header** with the logo
- A **footer** with an image and page numbers
- Sections for:
    - Investor details
    - Investment summary
    - Transaction details
    - End of statement message

I used **CSS `@page` rules** to set page size, margins, and running headers/footers. I also included **page-break-inside: avoid** for important sections to prevent them from splitting across pages.

---

### 2. Service Layer
I made a `PdfService` which:
- Takes a **map of data** (investor info, investments, transactions)
- Passes it to **Thymeleaf** to generate HTML
- Uses **OpenHTMLToPDF** to render the HTML into a PDF
- Saves the PDF to the project root as `statement.pdf`

I used `builder.useFastMode()` to make rendering faster, and provided a **base URI** so images and CSS in the HTML are correctly loaded.

---

### 3. Controller
I created a `PdfController` with a `/generate-pdf` endpoint:
1. Created **mock data** for investor details.
2. Generated **multiple investments** dynamically to test **multi-page behavior**.
3. Generated **multiple transactions** dynamically to simulate realistic data.
4. Passed all the data to `PdfService` to generate the PDF.

I made helper methods like `getTransactions()` to keep the code organized.

---

### 4. Mock Data
I intentionally created **25 investments** and **30 transactions** to test:
- Proper calculation of **total investments**
- **Unit balances** updating correctly
- PDF spanning multiple pages without breaking tables awkwardly

---

### 5. Tools & Libraries
- **Thymeleaf**: HTML templating engine
- **OpenHTMLToPDF**: Converts HTML + CSS to PDF
- **Java BigDecimal**: For precise financial calculations

---



