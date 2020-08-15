package com.service;

import com.Service.ProcessService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.times;

public class SMSSendTest {
//    @Autowired
//    private ProcessService processService = new ProcessService();
    @Test
    public void SMSSendTest(){
        String ACCOUNT_SID = System.getProperty("account_sid");
        String AUTH_TOKEN = System.getProperty("auth_token");
        ProcessService processService = new ProcessService();
        String receiveNumber = "+17863515568";
        String sendNumber = "+14107093676";
        String messageBody = "hi";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        MessageCreator creator = processService.SMS(receiveNumber, sendNumber, messageBody);
        processService.twillioSMSSend(creator);
    }
}
