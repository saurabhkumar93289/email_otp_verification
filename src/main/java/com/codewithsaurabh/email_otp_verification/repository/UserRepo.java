package com.codewithsaurabh.email_otp_verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsaurabh.email_otp_verification.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
