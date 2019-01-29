package com.manos.prototype.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private Environment env;

	private Transport transport;
	
	private Session session;
	
	@Bean
	@Autowired
    public JavaMailSender javaMailService() {
		Properties props = new Properties();
		props.setProperty("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
		props.setProperty("mail.smtp.port", env.getProperty("mail.smtp.port"));
		props.setProperty("mail.smtp.host", env.getProperty("mail.smtp.host"));
		props.setProperty("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
//		props.setProperty("mail.debug", "true");
		
		session = Session.getInstance(props, new Authenticator() {          
		      @Override
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(env.getProperty("mail.smtp.username"),
		        		  env.getProperty("mail.smtp.password"));          
		      }       
		  });
		
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(props);
        return javaMailSender;
    }

	@Override
	public void sendNewPassword(String email, String password) {
		Message message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress("manos-mark@hotmail.com"));
			message.setSubject("New Password request");
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setText("Your new password is: " + password);
			
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
