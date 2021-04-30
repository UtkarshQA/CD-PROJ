package com.MyAccount.Utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailUtil extends TestBase {

	public void send_report(String reportFileName) throws Exception {

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("FromEmail"),
						properties.getProperty("EmailPassword"));
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(properties.getProperty("FromEmail"), "Automation Mail"));

			String string = properties.getProperty("ToEmail");
			String[] toEmails = string.split(",");

			for (int i = 0; i < toEmails.length; i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
			}

			message.setSubject("Test Subject");

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("Text mail .....");

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();

			DataSource source = new FileDataSource(reportFileName);
			messageBodyPart.setDataHandler(new DataHandler(source));

			String[] parts = reportFileName.split("reports");
			String part2 = parts[1];
			
			System.out.println(part2);
			
			messageBodyPart.setFileName(part2);
			
			multipart.addBodyPart(messageBodyPart);

			

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("EMail Sent Successfully with attachment!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void backup_send_report(String reportFile) throws Exception {

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("FromEmail"),
						properties.getProperty("EmailPassword"));
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(properties.getProperty("FromEmail"), "Automation Mail"));

			String string = properties.getProperty("ToEmail");
			String[] toEmails = string.split(",");

			for (int i = 0; i < toEmails.length; i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
			}

//            Multipart multipart = new MimeMultipart();
//            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//            
//            
//            DataSource source = new FileDataSource("E:\\Utkarsh\\Automation\\MooseVIP\\reports");  
//            messageBodyPart2.setDataHandler(new DataHandler(source));  
//            messageBodyPart2.setFileName("report_05-11-2019 23-22-49.html");
//            
//            System.out.println(reportFile);
//                
//            multipart.addBodyPart(messageBodyPart2);
//            message.setContent(multipart );  
//            message.setSubject("Test Subject");
//            message.setText("Test email Text");
//
//            
//            // send message
//            Transport.send(message);
//            System.out.println("message sent successfully");

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("Text mail .....");

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "E:\\Utkarsh\\Automation\\MooseVIP\\reports\\report_05-11-2019 23-22-49.html";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("EMail Sent Successfully with attachment!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
