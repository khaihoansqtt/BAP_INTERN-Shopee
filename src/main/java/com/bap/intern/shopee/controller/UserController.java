package com.bap.intern.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bap.intern.shopee.dto.user.PatchUserReq;
import com.bap.intern.shopee.service.UserService;
import com.bap.intern.shopee.util.AuthUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthUtil authUtil;
	
	@PatchMapping()
	public ResponseEntity<?> patchUser(@Valid @RequestBody PatchUserReq req, Authentication authentication) {
		int userId = authUtil.getUserId(authentication);
		return new ResponseEntity(userService.patchUser(userId, req), HttpStatus.OK);
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteUser(Authentication authentication) {
		int userId = authUtil.getUserId(authentication);
		userService.deleteUser(userId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
