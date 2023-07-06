package com.test.Test.Controller;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.Test.Service.EmailService;

@RestController
@CrossOrigin
@RequestMapping("/otpsend")
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/send-otp")
    public void sendOTP(@RequestBody String email) {
        String otp = emailService.generateOTP();
        emailService.saveVerificationOTP(email, otp);
        
        sendEmail(email, "OTP for Email Verification", "Your OTP is: " + otp);

    }
	
	
	@PostMapping("/verify-otp")
    public boolean verifyOTP(@RequestBody VerifyOTPRequest request) {
        return emailService.verifyOTP(request.getEmail(), request.getOtp());
    }
	
	private static class VerifyOTPRequest {
	    private String email;
	    private String otp;

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getOtp() {
	        return otp;
	    }

	    public void setOtp(String otp) {
	        this.otp = otp;
	    }

	}
	 
	 private void sendEmail(String recipientEmail, String subject, String body) {
	        // Set up email properties
	        Properties props = new Properties();
	        final String username = "rishi2kita@gmail.com"; // Replace with your email address
	        final String password = "mmnscbrptmtmzrgg";  // Replace with your smtp password
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        // Set up authentication
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
	        

	        try {
	        	
	            Message message = new MimeMessage(session);

	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

	            message.setSubject(subject);
	            message.setText(body);

	            Transport.send(message);
	            System.out.println("Email sent successfully!");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }

}
