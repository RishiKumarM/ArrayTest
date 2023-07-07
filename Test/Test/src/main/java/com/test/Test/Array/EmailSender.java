package com.test.Test.Array;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	  public String generateOTP() {
	        Random random = new Random();
	        int otpNumber = 100_000 + random.nextInt(900_000);
	        return String.valueOf(otpNumber);
	    }
	  
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Email Address: ");
        String email = scanner.nextLine();
        System.out.println("Please wait ....");
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP host
        props.put("mail.smtp.port", "587"); // Use port 587 for TLS/STARTTLS

        // Set up authentication credentials
        final String username = "rishi2kita@gmail.com"; // Replace with your email address
        final String password = "mmns cbrp tmtm zrgg"; // Replace with your email password

        // Set up session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set the email subject and body
            Random random = new Random();
			int otp = (int) (Math.random() * (100000 + random.nextInt(999999)));
            message.setSubject("Hello from MySide ");
            message.setText("This is a test email sent by RKM." + " With Test OTP: " + otp + " ");

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully! Check Your Email!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

