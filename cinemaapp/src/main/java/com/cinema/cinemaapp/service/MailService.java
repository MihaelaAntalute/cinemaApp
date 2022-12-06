package com.cinema.cinemaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cursjustcodeit@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

//    public void sendEmail(){
//        final String fromEmail = "myemailid@gmail.com"; //requires valid gmail id
//        final String password = "mypassword"; // correct password for gmail id
//        final String toEmail = "myemail@yahoo.com"; // can be any email id
//
//        System.out.println("TLSEmail Start");
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//        props.put("mail.smtp.port", "587"); //TLS Port
//        props.put("mail.smtp.auth", "true"); //enable authentication
//        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//
//        //create Authenticator object to pass in Session.getInstance argument
//        Authenticator auth = new Authenticator() {
//            //override the getPasswordAuthentication method
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, password);
//            }
//        };
//        Session session = Session.getInstance(props, auth);
//
//        EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
//    }
}
