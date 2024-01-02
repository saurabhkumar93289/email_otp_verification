package com.codewithsaurabh.email_otp_verification.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithsaurabh.email_otp_verification.entity.User;
import com.codewithsaurabh.email_otp_verification.payload.RequestDto;
import com.codewithsaurabh.email_otp_verification.payload.ResponseDto;
import com.codewithsaurabh.email_otp_verification.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
	
	public ResponseDto registerUser(RequestDto request){
		ResponseDto res = new ResponseDto();
		
		User existingUser = this.userRepo.findByEmail(request.getEmail());
		if(existingUser != null) {
			res.setMessage("User already registered.");
		} else {
			Random r = new Random();
			String otp = String.format("%06d", r.nextInt(100000));
			
			User newUser = new User();
			newUser.setUsername(request.getUsername());
			newUser.setEmail(request.getEmail());
			newUser.setOtp(otp);
			newUser.setVerified(false);
			
		 User savedUser = this.userRepo.save(newUser);
		 String subject = "Email Verfication";
		 String body = "Your verification OTP is "+savedUser.getOtp();
		 //Email Send
		 this.emailService.sendEmail(savedUser.getEmail(), subject, body);
		 
		 res.setUser_id(savedUser.getUser_id());
		 res.setUsername(savedUser.getUsername());
		 res.setEmail(savedUser.getEmail());
		 res.setMessage("OTP sent successfully!");
		 
		}
		
		return res;
		
	}
	
	
	public String verifyUser(String email, String otp) {
		String response = "";
		User user = this.userRepo.findByEmail(email);
		
		if(user != null && user.isVerified()) {
			response = "User is already verified.";
		}else if(otp.equals(user.getOtp())) {
			user.setVerified(true);
			this.userRepo.save(user);
			response = "User verified successfully.";
		}else {
			response = "User not verified.";
		}
		
		return response;
		
	}
	
	
}
