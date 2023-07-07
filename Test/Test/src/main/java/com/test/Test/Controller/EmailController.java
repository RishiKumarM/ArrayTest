package com.test.Test.Controller;

import com.test.Test.Service.EmailService;
import com.test.Test.entity.EmailMsg;
import com.test.Test.entity.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public ResponseEntity<ResponseMessage> sendOTP(@RequestBody EmailMsg emailMsg) {
        return emailService.sendOTP(emailMsg);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseMessage> verifyOTP(@RequestParam String email, @RequestParam int otp) {
        return emailService.verifyOTP(email, otp);
    }

    @GetMapping("/data")
    public ResponseEntity<?> getAllData() {
        return ResponseEntity.ok(emailService.getAllData());
    }

    @GetMapping("/data/{email}")
    public ResponseEntity<?> getSingleData(@PathVariable String email) {
        EmailMsg emailMsg = emailService.getSingleData(email);
        if (emailMsg != null) {
            return ResponseEntity.ok(emailMsg);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }
    }
}
