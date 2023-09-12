package com.bap.intern.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bap.intern.shopee.dto.auth.LoginReq;
import com.bap.intern.shopee.dto.auth.RegisterReq;
import com.bap.intern.shopee.dto.auth.RegisterRes;
import com.bap.intern.shopee.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    
    @PostMapping("/register")
    public ResponseEntity<RegisterRes> register(@Valid @RequestBody RegisterReq registerReq) throws Exception {
    	RegisterRes res = authService.register(registerReq);
        return new ResponseEntity<RegisterRes>(res, HttpStatus.OK);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq) throws Exception {
    	String email = loginReq.getEmail();
    	String password = loginReq.getPassword();
        return ResponseEntity.ok(authService.login(email, password));
    }
}
