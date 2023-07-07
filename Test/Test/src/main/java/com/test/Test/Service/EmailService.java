package com.test.Test.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.Test.Repo.EmailRepo;
import com.test.Test.entity.EmailMsg;
import com.test.Test.entity.ResponseMessage;

@Service
public class EmailService {

    @Autowired
    private EmailRepo emailRepo;

    public ResponseEntity<ResponseMessage> sendOTP(EmailMsg emailVerification2) {
        EmailMsg emailVerification = new EmailMsg();
        emailVerification.setEmail(emailVerification2.getEmail());
        emailVerification.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        ResponseMessage responseMessage = validateClientDetail(emailVerification2);

        if (responseMessage.getStatusCode().equals("0")) {
            int otp = generateOTP();
            emailVerification.setOtp(otp);
            sendEmailWithOTP(emailVerification);
            
            // Schedule deletion after 5 minutes
            scheduleDeletion(emailVerification);
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    private void sendEmailWithOTP(EmailMsg emailMsg) {
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
        session.setDebug(true);

        try {
            // Create a new message
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailMsg.getEmail()));

            // Set the email subject and body
            String otpText = "Hello this is the test email by me(RKM). And Your One-Time-Password is: " + emailMsg.getOtp();
            message.setSubject("OTP Verification");
            message.setText(otpText);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully! Check your email for the OTP.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailRepo.save(emailMsg);
    }
    
    private void scheduleDeletion(EmailMsg emailMsg) {
        LocalDateTime expirationTime = emailMsg.getExpirationTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(now, expirationTime);
        long delay = duration.toMillis();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                emailRepo.delete(emailMsg);
            }
        }, delay);
    }
    
    private ResponseMessage validateClientDetail(EmailMsg emailVerification2) {
        EmailMsg dbEmailDetail = emailRepo.findByEmail(emailVerification2.getEmail());

        if (dbEmailDetail != null) {
            if (dbEmailDetail.getEmail().equalsIgnoreCase(emailVerification2.getEmail())) {
                return new ResponseMessage("User with provided email already exists", "1");
            } else {
                return new ResponseMessage("User already exists, but email is not registered", "2");
            }
        } else {
            return new ResponseMessage("New user for registration", "0");
        }
    }

    public ResponseEntity<ResponseMessage> verifyOTP(String email, int otp) {
        EmailMsg emailVerification = emailRepo.findByEmail(email);
        ResponseMessage responseMessage;

        if (emailVerification == null || emailVerification.getOtp() != otp) {
            responseMessage = new ResponseMessage("OTP is incorrect or null", "1");
        } else if (emailVerification.getExpirationTime().isBefore(LocalDateTime.now())) {
            emailRepo.delete(emailVerification);
            responseMessage = new ResponseMessage("OTP has expired", "2");
        } else {
            emailRepo.delete(emailVerification);
            responseMessage = new ResponseMessage("OTP is correct", "0");
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    private int generateOTP() {
        int min = 100000;
        int max = 999999;
        return new Random().nextInt(max - min + 1) + min;
    }

    public List<EmailMsg> getAllData() {
        return emailRepo.findAll();
    }

    public EmailMsg getSingleData(String email) {
        return emailRepo.findByEmail(email);
    }
}
