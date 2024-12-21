package com.Email.MailSending.service;
//
//import com.Email.MailSending.controller.PdfHeaderFooter;
//import com.Email.MailSending.module.Users;
//import com.Email.MailSending.repository.UserRepository;
//import com.lowagie.text.*;
//import com.lowagie.text.Font;
//import com.lowagie.text.Image;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.awt.*;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//@Service
//public class Pdfservice {
//
//    private final UserRepository userRepository;
//    private final Logger logger = LoggerFactory.getLogger(Pdfservice.class);
//
//    public Pdfservice(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public ByteArrayInputStream createPdf(Long id) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try (Document document = new Document(PageSize.A4, 36, 36, 72, 72)) {
//            logger.info("Finding user with ID: {}", id);
//            Users user = userRepository.findById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            writer.setPageEvent(new PdfHeaderFooter());
//            document.open();
//
//            // Add Title
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.black);
//            Paragraph title = new Paragraph("User Information Report", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            document.add(Chunk.NEWLINE); // Add space
//
//            // Add User Details
////            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12,Color.darkGray);
////            document.add(new Paragraph("Name: " + user.getName(), contentFont));
////            document.add(new Paragraph("Email: " + user.getEmail(), contentFont));
////            document.add(new Paragraph("Mobile Number: " + user.getPhoneNumber(), contentFont));
////
////            document.add(Chunk.NEWLINE);
//
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(12f);
//            table.setSpacingAfter(12f);
//
//
//            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.white);
//            PdfPCell header1 = new PdfPCell(new Phrase("Field", tableHeaderFont));
//            header1.setBackgroundColor(Color.GRAY);
//            PdfPCell header2 = new PdfPCell(new Phrase("Value", tableHeaderFont));
//            header2.setBackgroundColor(Color.GRAY);
//            PdfPCell header3 = new PdfPCell(new Phrase("Remarks", tableHeaderFont));
//            header3.setBackgroundColor(Color.GRAY);
//
//            table.addCell(header1);
//            table.addCell(header2);
//            table.addCell(header3);
//
//            // Add Sample Data
//            table.addCell("Name");
//            table.addCell(user.getName());
//            table.addCell("Valid");
//
//            table.addCell("Email");
//            table.addCell(user.getEmail());
//            table.addCell("Verified");
//
//            table.addCell("Phone");
//            table.addCell(user.getPhoneNumber());
//            table.addCell("Active");
//
//            document.add(table);
//
//        } catch (Exception e) {
//            logger.error("Error generating PDF: {}", e.getMessage());
//            throw new RuntimeException("PDF generation failed");
//        }
//
//        return new ByteArrayInputStream(outputStream.toByteArray());
//    }
//}
//@Service
//public class Pdfservice {
//
//    private final UserRepository userRepository;
//    private final Logger logger = LoggerFactory.getLogger(Pdfservice.class);
//
//    public Pdfservice(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public ByteArrayInputStream createUserPdf(Long id) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        Document document = new Document(PageSize.A4, 36, 36, 72, 72); // Add margins
//        try {
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            writer.setPageEvent(new PdfHeaderFooter()); // Set custom header and footer
//            document.open();
//
//            // Fetch single user data
//            Users user = userRepository.findById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//            // Title
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
//            Paragraph title = new Paragraph("User Information Report", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            document.add(Chunk.NEWLINE); // Add space
//
//            // User Details
//            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.DARK_GRAY);
//            document.add(new Paragraph("Name: " + user.getName(), contentFont));
//            document.add(new Paragraph("Email: " + user.getEmail(), contentFont));
//            document.add(new Paragraph("Mobile Number: " + user.getPhoneNumber(), contentFont));
//
//        } catch (Exception e) {
//            logger.error("Error generating PDF: {}", e.getMessage());
//            throw new RuntimeException("PDF generation failed");
//        } finally {
//            document.close();
//        }
//
//        return new ByteArrayInputStream(outputStream.toByteArray());
//    }
//
//    public ByteArrayInputStream createAllUsersPdf() {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        Document document = new Document(PageSize.A4, 36, 36, 72, 72); // Add margins
//        try {
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            writer.setPageEvent(new PdfHeaderFooter()); // Set custom header and footer
//            document.open();
//
//            // Title
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
//            Paragraph title = new Paragraph("All Users Information Report", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            document.add(Chunk.NEWLINE); // Add space
//
//            // Table with All Users Data
//            PdfPTable table = new PdfPTable(4); // Adjust number of columns as needed
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(10f);
//            table.setSpacingAfter(10f);
//
//            // Table Header
//            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
//            PdfPCell header1 = new PdfPCell(new Phrase("ID", tableHeaderFont));
//            header1.setBackgroundColor(Color.GRAY);
//            PdfPCell header2 = new PdfPCell(new Phrase("Name", tableHeaderFont));
//            header2.setBackgroundColor(Color.GRAY);
//            PdfPCell header3 = new PdfPCell(new Phrase("Email", tableHeaderFont));
//            header3.setBackgroundColor(Color.GRAY);
//            PdfPCell header4 = new PdfPCell(new Phrase("Phone", tableHeaderFont));
//            header4.setBackgroundColor(Color.GRAY);
//
//            table.addCell(header1);
//            table.addCell(header2);
//            table.addCell(header3);
//            table.addCell(header4);
//
//            // Add Data Rows
//            for (Users user : userRepository.findAll()) {
//                table.addCell(String.valueOf(user.getId()));
//                table.addCell(user.getName());
//                table.addCell(user.getEmail());
//                table.addCell(user.getPhoneNumber());
//            }
//
//            document.add(table);
//
//        } catch (Exception e) {
//            logger.error("Error generating PDF: {}", e.getMessage());
//            throw new RuntimeException("PDF generation failed");
//        } finally {
//            document.close();
//        }
//
//        return new ByteArrayInputStream(outputStream.toByteArray());
//    }
//}
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtils;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.data.category.DefaultCategoryDataset;

//@Service
//public class Pdfservice {
//
//    private final UserRepository userRepository;
//    private final Logger logger = LoggerFactory.getLogger(Pdfservice.class);
//
//    public Pdfservice(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public ByteArrayInputStream createUserPdf(Long id) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        Document document = new Document(PageSize.A4, 36, 36, 72, 72);
//        try {
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            writer.setPageEvent(new PdfHeaderFooter());
//            document.open();
//
//            Users user = userRepository.findById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
//            Paragraph title = new Paragraph("User Information Report", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            document.add(Chunk.NEWLINE);
//            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.DARK_GRAY);
//            document.add(new Paragraph("Name: " + user.getName(), contentFont));
//            document.add(new Paragraph("Email: " + user.getEmail(), contentFont));
//            document.add(new Paragraph("Mobile Number: " + user.getPhoneNumber(), contentFont));
//
//            document.add(Chunk.NEWLINE);
//            JFreeChart chart = createPieChart();
//            ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
//            ChartUtils.writeChartAsPNG(chartOut, chart, 500, 300);
//            document.add(Image.getInstance(chartOut.toByteArray()));
//
//        } catch (Exception e) {
//            logger.error("Error generating PDF: {}", e.getMessage());
//            throw new RuntimeException("PDF generation failed");
//        } finally {
//            document.close();
//        }
//
//        return new ByteArrayInputStream(outputStream.toByteArray());
//    }
//
//    public ByteArrayInputStream createAllUsersPdf() {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        Document document = new Document(PageSize.A4, 36, 36, 72, 72);
//        try {
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            writer.setPageEvent(new PdfHeaderFooter());
//            document.open();
//
//            // Title
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
//            Paragraph title = new Paragraph("All Users Information Report", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            document.add(Chunk.NEWLINE);
//
//
//            PdfPTable table = new PdfPTable(4);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(10f);
//            table.setSpacingAfter(10f);
//
//
//            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
//            PdfPCell header1 = new PdfPCell(new Phrase("ID", tableHeaderFont));
//            header1.setBackgroundColor(Color.GRAY);
//            PdfPCell header2 = new PdfPCell(new Phrase("Name", tableHeaderFont));
//            header2.setBackgroundColor(Color.GRAY);
//            PdfPCell header3 = new PdfPCell(new Phrase("Email", tableHeaderFont));
//            header3.setBackgroundColor(Color.GRAY);
//            PdfPCell header4 = new PdfPCell(new Phrase("Phone", tableHeaderFont));
//            header4.setBackgroundColor(Color.GRAY);
//
//            table.addCell(header1);
//            table.addCell(header2);
//            table.addCell(header3);
//            table.addCell(header4);
//
//            for (Users user : userRepository.findAll()) {
//                table.addCell(String.valueOf(user.getId()));
//                table.addCell(user.getName());
//                table.addCell(user.getEmail());
//                table.addCell(user.getPhoneNumber());
//            }
//
//            document.add(table);
//
//            JFreeChart chart = createPieChart();
//            ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
//            ChartUtils.writeChartAsPNG(chartOut, chart, 500, 300);
//            document.add(Image.getInstance(chartOut.toByteArray()));
//
//        } catch (Exception e) {
//            logger.error("Error generating PDF: {}", e.getMessage());
//            throw new RuntimeException("PDF generation failed");
//        } finally {
//            document.close();
//        }
//
//        return new ByteArrayInputStream(outputStream.toByteArray());
//    }

//    private JFreeChart createPieChart() {
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue("Active", 12);
//        dataset.setValue("Inactive", 15);
//        JFreeChart chart = ChartFactory.createPieChart(
//                "User Status Distribution", // Chart Title
//                dataset,             // Dataset
//                true,                // Include legend
//                true,
//                false
//        );
//        return chart;
//    }
//}
//


//  Generate PDF using thymeleaf

import com.Email.MailSending.module.Users;
import com.Email.MailSending.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class Pdfservice {


    private final UserRepository userRepository;


    private final TemplateEngine templateEngine;

    @Autowired
    public Pdfservice(UserRepository userRepository, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
    }

    public ByteArrayInputStream createUserPdf(Long id) throws DocumentException {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Users user = optionalUser.get();

        Context context = new Context();
        context.setVariable("user", user);

        String html = templateEngine.process("user", context);
        return generatePdf(html);
    }

    public ByteArrayInputStream createAllUsersPdf() throws DocumentException {
        List<Users> users = userRepository.findAll();

        Context context = new Context();
        context.setVariable("users", users);

        String html = templateEngine.process("all-users", context);
        return generatePdf(html);
    }

    private ByteArrayInputStream generatePdf(String html) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }


}
