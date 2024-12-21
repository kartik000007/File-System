package com.Email.MailSending.service;

import com.Email.MailSending.module.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {


    private final JavaMailSender emailSender;
    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendWelcomeEmail(Users user) throws MessagingException, IOException {

        String htmlContent = buildWelcomeEmailContent(user);


        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Welcome to Our Service");
        helper.setText(htmlContent, true);

        emailSender.send(mimeMessage);
    }

    private String buildWelcomeEmailContent(Users user) throws IOException {

        File templateFile = ResourceUtils.getFile("classpath:templates/welcome_email_template.html");
        String templateContent = new String(Files.readAllBytes(Paths.get(templateFile.toURI())));


        return templateContent.replace("{{name}}", user.getName())
                .replace("{{email}}", user.getEmail());
    }
}
