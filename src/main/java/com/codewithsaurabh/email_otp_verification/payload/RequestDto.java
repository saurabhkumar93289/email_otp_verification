package com.codewithsaurabh.email_otp_verification.payload;

import lombok.Data;

@Data
public class RequestDto {
	private String username;
	private String email;

}
