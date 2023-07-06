package com.test.Test.Service;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.Test.Repo.EmailRepo;
import com.test.Test.entity.Email;

@Service
public class EmailService {
	
	@Autowired
	private EmailRepo emailRepo;
	
	public String generateOTP() {
        Random random = new Random();
        int otpNumber = 100_000 + random.nextInt(900_000);
        return String.valueOf(otpNumber);
    }
	
	 public void saveVerificationOTP(String email, String otp) {
	        Email emailVerification = new Email();
	        emailVerification.setEmail(email);
	        emailVerification.setOtp(otp);
	        emailVerification.setExpirationTime(LocalDateTime.now().plusMinutes(5));
	        emailRepo.save(emailVerification);
	    }
	 public boolean verifyOTP(String email, String otp) {
	        Email emailVerification = emailRepo.findByEmail(email);

	        if (emailVerification == null || !emailVerification.getOtp().equals(otp)) {
	            return false;
	        }

	        if (emailVerification.getExpirationTime().isBefore(LocalDateTime.now())) {
	            // OTP expired, delete from database
	        	emailRepo.delete(emailVerification);
	            return false;
	        }

	        // OTP is valid
	        emailRepo.delete(emailVerification);
	        return true;
	    } 
	
}
