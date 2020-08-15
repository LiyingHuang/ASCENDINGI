package com.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ProcessService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SendGrid sendGrid;
    private String registrationTemplateId = "d-51e12461571f4bc385acb53d26df1d37";
    String ACCOUNT_SID = System.getProperty("account_sid");
    String AUTH_TOKEN = System.getProperty("auth_token");

    @JmsListener(destination = "shopping-platform-queue")
    public void processMessage(String msg_json) throws IOException {
        System.out.println(msg_json);
        logger.debug("Processing Message: " + msg_json);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> emailDetails = mapper.readValue(msg_json, new TypeReference<Map>(){});
        Mail mail = new Mail();

        // setTemplateId for different type email
        String subject = emailDetails.get("subject").toString();
        if (subject.equals("Registration Confirmation")) {
            mail.setTemplateId(registrationTemplateId);
        } else {
            return;
        }
        // setFrom: the email's from address.
        String from = emailDetails.get("from").toString();
        mail.setFrom(new Email(from)); //??

        // cast to MAP or LIST
        Map<String, String> requestInfo = (Map<String, String>) emailDetails.get("request");
        List<String> toEmails = (List<String>) emailDetails.get("to_emails");
        List<String> toUsernames = (List<String>) emailDetails.get("to_usernames");

        Personalization personalization = new Personalization();
        for (String to : toEmails) { personalization.addTo(new Email(to)); }
        personalization.setSubject(subject);
        personalization.addDynamicTemplateData("username", toUsernames.get(0));
        personalization.addDynamicTemplateData("request", requestInfo);
        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException e) {
            throw e;
        }
    }

    // SMS
    public MessageCreator SMS(String receiveNumber,String sendNumber,String messageBody) {
        MessageCreator message = Message.creator(
                new com.twilio.type.PhoneNumber(receiveNumber),
                new com.twilio.type.PhoneNumber(sendNumber),
                messageBody);
        return message;
    }
    public void twillioSMSSend(MessageCreator creator){
        creator.create();
    }
}

