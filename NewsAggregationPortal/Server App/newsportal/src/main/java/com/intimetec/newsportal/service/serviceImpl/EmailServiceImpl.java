package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String emailSubject, String emailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jsohan678@gmail.com"); // Must match configured email
        message.setTo(to);
        message.setSubject(emailSubject);
        message.setText(emailBody);

        mailSender.send(message);
        System.out.println("📧 Email sent successfully !");
    }
}
