package com.Email.MailSending.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    // Initialize Twilio with credentials
    public void sendMessage(String toPhoneNumber, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            messageBody)
                    .create();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send SMS via Twilio", e);
        }
    }
}

