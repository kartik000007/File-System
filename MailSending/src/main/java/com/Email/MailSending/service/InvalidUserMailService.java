package com.Email.MailSending.service;


import com.Email.MailSending.dto.InvalidUserDetailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class InvalidUserMailService {

    @Autowired
    private ExcelGeneratorService excelGeneratorService;

    @Autowired
    private JavaMailSender mailSender;

    public void sendInvalidUsersEmail(List<InvalidUserDetailDTO> invalidUsers, String recipientEmail) throws IOException {
        ByteArrayInputStream excelStream = excelGeneratorService.generateExcelForInvalidUsers(invalidUsers);

            Path outputPath = Paths.get("D://Practice//InvalidUsersStoreInLocal.xlsx");
            System.out.println("path set ");
            Files.copy(excelStream, outputPath, StandardCopyOption.REPLACE_EXISTING);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Validation Errors in Uploaded File");
            helper.setText("Dear User,\n\nPlease find attached an Excel file containing the rows that failed validation.\n\nBest Regards,\nYour Team");

            helper.addAttachment("emailAndPhoneNumberInvalid.xlsx", () -> excelStream);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}