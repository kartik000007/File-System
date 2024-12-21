package com.Email.MailSending.controller;

import com.Email.MailSending.service.Pdfservice;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

    @RestController
    @RequestMapping("/pdf")
    public class PdfController {

        @Autowired
        private Pdfservice pdfservice;

//        @RequestMapping("/test")
//        public ResponseEntity<String> test() {
//            return ResponseEntity.ok("PDF Controller is active");
//        }
        @GetMapping("/create/{id}")
        public ResponseEntity<InputStreamResource> generatePdf(@PathVariable Long id) {
            try {
                ByteArrayInputStream inputStream = pdfservice.createUserPdf(id);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=user.pdf");

                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(inputStream));
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        }

        @GetMapping("/all-users")
        public ResponseEntity<InputStreamResource> generateAllUsersPdf() throws DocumentException {
            ByteArrayInputStream inputStream = pdfservice.createAllUsersPdf();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=all-users.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));
        }
    }

