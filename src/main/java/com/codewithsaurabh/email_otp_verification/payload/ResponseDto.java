package com.codewithsaurabh.email_otp_verification.payload;

import lombok.Data;

@Data
public class ResponseDto {
private Long user_id;
 private String username;
 private String email;
 private boolean verified;
 private String message;
}
