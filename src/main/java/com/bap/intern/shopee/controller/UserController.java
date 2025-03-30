package com.bap.intern.shopee.controller;

import com.bap.intern.shopee.entity.Category;
import com.bap.intern.shopee.listener.TestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
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

import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	final UserService userService;
	final AuthUtil authUtil;
	final ApplicationEventPublisher eventPublisher;

	@GetMapping("/event")
	public String success() {
		System.out.println("hahaha");
		var cate = new Category();
		var a = new TestEvent(cate);
		eventPublisher.publishEvent(a);
		IntStream.range(0, 10).forEach(System.out::println);
		return cate.getName();
	}
	
	@PatchMapping()
	public ResponseEntity<?> patchUser(@Valid @RequestBody PatchUserReq req, Authentication authentication) {
		int userId = authUtil.getUserId(authentication);
		return new ResponseEntity(userService.patchUser(userId, req), HttpStatus.OK);
	}
	
	@GetMapping("/hello")
	public String hello(Pageable pageable) {
		System.out.println(pageable);
		return "hello";
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteUser(Authentication authentication) {
		int userId = authUtil.getUserId(authentication);
		userService.deleteUser(userId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
